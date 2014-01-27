package com.stewsters.util.pathing.twoDimention.djikstraMap;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.pathing.twoDimention.shared.SortedList2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

import java.util.ArrayList;
import java.util.Map;

public class DjikstraMap2d implements PathingMap2d {


    /**
     * The set of nodes that have been searched through
     */
    private ArrayList closed = new ArrayList();
    /**
     * The set of nodes that we do not yet consider fully searched
     */
    private SortedList2d open = new SortedList2d();

    /**
     * The map being searched
     */
    private TileBasedMap2d map;
    /**
     * The maximum depth of search we're willing to accept before giving up
     */
    private int maxSearchDistance;

    /**
     * The complete set of nodes across the map
     */
    private PathNode2d[][] nodes;


    /**
     * True if we allow diaganol movement
     */
    private boolean allowDiagMovement;

    public DjikstraMap2d(TileBasedMap2d map, int maxSearchDistance, boolean allowDiagMovement) {
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;

        nodes = new PathNode2d[map.getWidthInTiles()][map.getHeightInTiles()];
        for (int x = 0; x < map.getWidthInTiles(); x++) {
            for (int y = 0; y < map.getHeightInTiles(); y++) {

                nodes[x][y] = new PathNode2d(x, y);
            }
        }

    }

    @Override
    public void recalculate(Map<PathNode2d, Float> sourceTiles, Mover2d mover) {
        closed.clear();
        open.clear();

        for (Map.Entry<PathNode2d, Float> tileCost : sourceTiles.entrySet()) {
            int x = tileCost.getKey().x;
            int y = tileCost.getKey().y;
            nodes[x][y].cost = tileCost.getValue();
            nodes[x][y].depth = 0;
            open.add(nodes[x][y]);
        }


        //TODO: had to comment this out, it will provide some crucial errors later on
        // nodes[tx][ty][tz].parent = null;
        // while we haven't exceeded our max search depth
        int maxDepth = 0;
//        PathNode2d target = null;
//        FullPath2d path = null;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode2d current = getFirstInOpen();

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

                        // if we're not allowing diaganol movement then only
                        // one of x or y can be set

                        if (!allowDiagMovement) {
                            if ((x != 0) && (y != 0)) {
                                continue;
                            }
                        }

                        // determine the location of the neighbour and evaluate it

                        int xp = x + current.x;
                        int yp = y + current.y;

                        if (isValidLocation(mover, xp, yp)) {
                            // the cost to get to this PathNode is cost the current plus the movement
                            // cost to reach this node. Note that the heuristic value is only used
                            // in the sorted open list

                            float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, xp, yp);
                            PathNode2d neighbour = nodes[xp][yp];
                            map.pathFinderVisited(xp, yp);

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

    }

    /**
     * Get the first element from the open list. This is the next
     * one to be searched.
     *
     * @return The first element in the open list
     */
    protected PathNode2d getFirstInOpen() {
        return (PathNode2d) open.first();
    }

    /**
     * Add a PathNode to the open list
     *
     * @param node The PathNode to be added to the open list
     */
    protected void addToOpen(PathNode2d node) {
        open.add(node);
    }

    /**
     * Check if a PathNode is in the open list
     *
     * @param node The PathNode to check for
     * @return True if the PathNode given is in the open list
     */
    protected boolean inOpenList(PathNode2d node) {
        return open.contains(node);
    }

    /**
     * Remove a PathNode from the open list
     *
     * @param node The PathNode to remove from the open list
     */
    protected void removeFromOpen(PathNode2d node) {
        open.remove(node);
    }

    /**
     * Add a PathNode to the closed list
     *
     * @param node The PathNode to add to the closed list
     */
    protected void addToClosed(PathNode2d node) {
        closed.add(node);
    }

    /**
     * Check if the PathNode supplied is in the closed list
     *
     * @param node The PathNode to search for
     * @return True if the PathNode specified is in the closed list
     */
    protected boolean inClosedList(PathNode2d node) {
        return closed.contains(node);
    }

    /**
     * Remove a PathNode from the closed list
     *
     * @param node The PathNode to remove from the closed list
     */
    protected void removeFromClosed(PathNode2d node) {
        closed.remove(node);
    }

    /**
     * Check if a given location is valid for the supplied mover
     *
     * @param mover The mover that would hold a given location
     * @param x     The x coordinate of the location to check
     * @param y     The y coordinate of the location to check
     * @return True if the location is valid for the given mover
     */
    protected boolean isValidLocation(Mover2d mover, int x, int y) {
        boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles());

        if ((!invalid)) {
            invalid = map.blocked(mover, nodes[x][y]);
        }

        return !invalid;
    }

    /**
     * Get the cost to move through a given location
     *
     * @param mover The entity that is being moved
     * @param sx    The x coordinate of the tile whose cost is being determined
     * @param sy    The y coordiante of the tile whose cost is being determined
     * @param tx    The x coordinate of the target location
     * @param ty    The y coordinate of the target location
     * @return The cost of movement through the given tile
     */
    public float getMovementCost(Mover2d mover, int sx, int sy, int tx, int ty) {
        return map.getCost(mover, sx, sy, tx, ty);
    }


    public PathNode2d[][] getNodes() {
        return nodes;
    }

    public float getCostAt(int x, int y) {
        if ((x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles())) {
            return 0f;
        }
        return nodes[x][y].cost;
    }

}