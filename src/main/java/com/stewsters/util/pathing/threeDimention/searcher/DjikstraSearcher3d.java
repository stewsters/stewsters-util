package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class DjikstraSearcher3d implements Searcher3d {

    /**
     * The set of nodes that have been searched through
     */
    private ArrayList<PathNode3d> closed = new ArrayList<PathNode3d>();
    /**
     * The set of nodes that we do not yet consider fully searched
     */
    private PriorityQueue<PathNode3d> open = new PriorityQueue<>();

    /**
     * The map being searched
     */
    private TileBasedMap3d map;
    /**
     * The maximum depth of search we're willing to accept before giving up
     */
    private int maxSearchDistance;

    /**
     * The complete set of nodes across the map
     */
    private PathNode3d[][][] nodes;
    /**
     * True if we allow diagonal movement
     */
    private boolean allowDiagMovement;

    public DjikstraSearcher3d(TileBasedMap3d map, int maxSearchDistance, boolean allowDiagMovement) {
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;

        nodes = new PathNode3d[map.getWidthInTiles()][map.getHeightInTiles()][map.getDepthInTiles()];
        for (int x = 0; x < map.getWidthInTiles(); x++) {
            for (int y = 0; y < map.getHeightInTiles(); y++) {
                for (int z = 0; z < map.getDepthInTiles(); z++) {
                    nodes[x][y][z] = new PathNode3d(x, y, z);
                }
            }
        }
    }

    @Override
    public FullPath3d search(Mover3d mover, int sx, int sy, int sz, Objective3d objective) {

        nodes[sx][sy][sz].cost = 0;
        nodes[sx][sy][sz].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[sx][sy][sz]);

        //TODO: had to comment this out, it will provide some crucial errors later on
//        nodes[tx][ty][tz].parent = null;
        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        PathNode3d target = null;
        FullPath3d path = null;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode3d current = getFirstInOpen();


            if (objective.satisfiedBy(current)) {
                //TODO: this should set the answer location
                target = current;
                break;
            }
            removeFromOpen(current);
            addToClosed(current);

            // search through all the neighbours of the current PathNode evaluating

            // them as next steps
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {
                        // not a neighbour, its the current tile

                        if ((x == 0) && (y == 0) && (z == 0)) {
                            continue;
                        }

                        // if we're not allowing diagonal movement then only
                        // one of x or y can be set

                        if (!allowDiagMovement) {
                            if (((x != 0) && (y != 0)) ||
                                ((y != 0) && (z != 0)) ||
                                ((z != 0) && (x != 0))) {
                                continue;
                            }
                        }

                        // determine the location of the neighbour and evaluate it

                        int xp = x + current.x;
                        int yp = y + current.y;
                        int zp = z + current.z;

                        if (isValidLocation(mover, sx, sy, sz, xp, yp, zp)) {
                            // the cost to get to this PathNode is cost the current plus the movement
                            // cost to reach this node. Note that the heuristic value is only used
                            // in the sorted open list

                            float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, current.z, xp, yp, zp);
                            PathNode3d neighbour = nodes[xp][yp][zp];
                            map.pathFinderVisited(xp, yp, zp);

                            // if the new cost we've determined for this PathNode is lower than
                            // it has been previously,
                            // there might have been a better path to get to
                            // this PathNode so it needs to be re-evaluated

                            if (nextStepCost < neighbour.cost) {
                                if (inOpenList(neighbour)) {
                                    removeFromOpen(neighbour);
                                }
                                if (inClosedList(neighbour)) {
                                    removeFromClosed(neighbour);
                                }
                            }

                            // if the PathNode hasn't already been processed and discarded then
                            // reset it's cost to our current cost and add it as a next possible
                            // step (i.e. to the open list)

                            if (!inOpenList(neighbour) && !(inClosedList(neighbour))) {
                                neighbour.cost = nextStepCost;
                                neighbour.heuristic = 0; //getHeuristicCost(mover, xp, yp, zp, tx, ty, tz);
                                maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                                addToOpen(neighbour);
                            }
                        }
                    }
                }
            }
        }

        // since we'e've run out of search
        // there was no path. Just return null

        if (target == null) {
            return null;
        }

        // At this point we've definitely found a path so we can uses the parent

        // references of the nodes to find out way from the target location back

        // to the start recording the nodes on the way.

        path = new FullPath3d();
        while (target != nodes[sx][sy][sz]) {
            path.prependStep(target.x, target.y, target.z);
            target = target.parent;
        }
        path.prependStep(sx, sy, sz);

//        jobPath.path = path;
        // thats it, we have our path
        return path;

    }

    /**
     * Get the first element from the open list. This is the next
     * one to be searched.
     *
     * @return The first element in the open list
     */
    protected PathNode3d getFirstInOpen() {
        return open.peek();
    }

    /**
     * Add a PathNode to the open list
     *
     * @param node The PathNode to be added to the open list
     */
    protected void addToOpen(PathNode3d node) {
        open.add(node);
    }

    /**
     * Check if a PathNode is in the open list
     *
     * @param node The PathNode to check for
     * @return True if the PathNode given is in the open list
     */
    protected boolean inOpenList(PathNode3d node) {
        return open.contains(node);
    }

    /**
     * Remove a PathNode from the open list
     *
     * @param node The PathNode to remove from the open list
     */
    protected void removeFromOpen(PathNode3d node) {
        open.remove(node);
    }

    /**
     * Add a PathNode to the closed list
     *
     * @param node The PathNode to add to the closed list
     */
    protected void addToClosed(PathNode3d node) {
        closed.add(node);
    }

    /**
     * Check if the PathNode supplied is in the closed list
     *
     * @param node The PathNode to search for
     * @return True if the PathNode specified is in the closed list
     */
    protected boolean inClosedList(PathNode3d node) {
        return closed.contains(node);
    }

    /**
     * Remove a PathNode from the closed list
     *
     * @param node The PathNode to remove from the closed list
     */
    protected void removeFromClosed(PathNode3d node) {
        closed.remove(node);
    }

    /**
     * Check if a given location is valid for the supplied mover
     *
     * @param mover The mover that would hold a given location
     * @param sx    The starting x coordinate
     * @param sy    The starting y coordinate
     * @param sz    The starting z coordinate
     * @param x     The x coordinate of the location to check
     * @param y     The y coordinate of the location to check
     * @param z     The z coordinate of the location to check
     * @return True if the location is valid for the given mover
     */
    protected boolean isValidLocation(Mover3d mover, int sx, int sy, int sz, int x, int y, int z) {
        boolean invalid = (x < 0) || (y < 0) || (z < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles()) || (z >= map.getDepthInTiles());

        if ((!invalid) && ((sx != x) || (sy != y))) {
            invalid = map.isBlocked(mover, nodes[x][y][z]);
        }

        return !invalid;
    }

    /**
     * Get the cost to move through a given location
     *
     * @param mover The entity that is being moved
     * @param sx    The x coordinate of the tile whose cost is being determined
     * @param sy    The y coordiante of the tile whose cost is being determined
     * @param sz    The y coordiante of the tile whose cost is being determined
     * @param tx    The x coordinate of the target location
     * @param ty    The y coordinate of the target location
     * @param tz    The z coordinate of the target location
     * @return The cost of movement through the given tile
     */
    public float getMovementCost(Mover3d mover, int sx, int sy, int sz, int tx, int ty, int tz) {
        return map.getCost(mover, sx, sy, sz, tx, ty, tz);
    }


}
