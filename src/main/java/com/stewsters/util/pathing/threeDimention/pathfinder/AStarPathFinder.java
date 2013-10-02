package com.stewsters.util.pathing.threeDimention.pathfinder;

import com.stewsters.util.pathing.threeDimention.shared.*;

import java.util.ArrayList;

/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path.
 *
 * @author Kevin Glass
 */
public class AStarPathFinder implements PathFinder {
    /**
     * The set of nodes that have been searched through
     */
    private ArrayList closed = new ArrayList();
    /**
     * The set of nodes that we do not yet consider fully searched
     */
    private SortedList open = new SortedList();

    /**
     * The map being searched
     */
    private TileBasedMap map;
    /**
     * The maximum depth of search we're willing to accept before giving up
     */
    private int maxSearchDistance;

    /**
     * The complete set of nodes across the map
     */
    private PathNode[][][] nodes;
    /**
     * True if we allow diaganol movement
     */
    private boolean allowDiagMovement;
    /**
     * The heuristic we're applying to determine which nodes to search first
     */
    private AStarHeuristic heuristic;

    /**
     * Create a path finder with the default heuristic - closest to target.
     *
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     * @param allowDiagMovement True if the search should try diagonal movement
     */
    public AStarPathFinder(TileBasedMap map, int maxSearchDistance, boolean allowDiagMovement) {
        this(map, maxSearchDistance, allowDiagMovement,(AStarHeuristic) new ClosestHeuristic());
    }

    /**
     * Create a path finder
     *
     * @param heuristic         The heuristic used to determine the search order of the map
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     * @param allowDiagMovement True if the search should try diaganol movement
     */
    public AStarPathFinder(TileBasedMap map, int maxSearchDistance,
                           boolean allowDiagMovement, AStarHeuristic heuristic) {
        this.heuristic = heuristic;
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;

        nodes = new PathNode[map.getWidthInTiles()][map.getHeightInTiles()][map.getDepthInTiles()];
        for (int x = 0; x < map.getWidthInTiles(); x++) {
            for (int y = 0; y < map.getHeightInTiles(); y++) {
                for (int z = 0; z < map.getDepthInTiles(); z++) {
                    nodes[x][y][z] = new PathNode(x, y, z);
                }
            }
        }
    }

    /**
     * @see PathFinder#findPath(com.stewsters.util.pathing.threeDimention.shared.Mover, int, int, int, int, int, int)
     */
    public FullPath findPath(Mover mover, int sx, int sy, int sz, int tx, int ty, int tz) {
        // easy first check, if the destination is blocked, we can't get there

        if (map.blocked(mover, nodes[tx][ ty][ tz])) {
            return null;
        }

        // initial state for A*. The closed group is empty. Only the starting
        // tile is in the open list and it's already there
        nodes[sx][sy][sz].cost = 0;
        nodes[sx][sy][sz].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[sx][sy][sz]);

        nodes[tx][ty][tz].parent = null;

        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode current = getFirstInOpen();
            if (current == nodes[tx][ty][tz]) {
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

                        // if we're not allowing diaganol movement then only
                        // one of x or y can be set

                        if (!allowDiagMovement) {
                            if ((x != 0) && (y != 0) && (z != 0)) {
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
                            PathNode neighbour = nodes[xp][yp][zp];
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
                                neighbour.heuristic = getHeuristicCost(mover, xp, yp, zp, tx, ty, tz);
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

        if (nodes[tx][ty][tz].parent == null) {
            return null;
        }

        // At this point we've definitely found a path so we can uses the parent

        // references of the nodes to find out way from the target location back

        // to the start recording the nodes on the way.

        FullPath path = new FullPath();
        PathNode target = nodes[tx][ty][tz];
        while (target != nodes[sx][sy][sz]) {
            path.prependStep(target.x, target.y, target.z);
            target = target.parent;
        }
        path.prependStep(sx, sy, sz);

        // thats it, we have our path

        return path;
    }

    /**
     * Get the first element from the open list. This is the next
     * one to be searched.
     *
     * @return The first element in the open list
     */
    protected PathNode getFirstInOpen() {
        return (PathNode) open.first();
    }

    /**
     * Add a PathNode to the open list
     *
     * @param node The PathNode to be added to the open list
     */
    protected void addToOpen(PathNode node) {
        open.add(node);
    }

    /**
     * Check if a PathNode is in the open list
     *
     * @param node The PathNode to check for
     * @return True if the PathNode given is in the open list
     */
    protected boolean inOpenList(PathNode node) {
        return open.contains(node);
    }

    /**
     * Remove a PathNode from the open list
     *
     * @param node The PathNode to remove from the open list
     */
    protected void removeFromOpen(PathNode node) {
        open.remove(node);
    }

    /**
     * Add a PathNode to the closed list
     *
     * @param node The PathNode to add to the closed list
     */
    protected void addToClosed(PathNode node) {
        closed.add(node);
    }

    /**
     * Check if the PathNode supplied is in the closed list
     *
     * @param node The PathNode to search for
     * @return True if the PathNode specified is in the closed list
     */
    protected boolean inClosedList(PathNode node) {
        return closed.contains(node);
    }

    /**
     * Remove a PathNode from the closed list
     *
     * @param node The PathNode to remove from the closed list
     */
    protected void removeFromClosed(PathNode node) {
        closed.remove(node);
    }

    /**
     * Check if a given location is valid for the supplied mover
     *
     * @param mover The mover that would hold a given location
     * @param sx    The starting x coordinate
     * @param sy    The starting y coordinate
     * @param x     The x coordinate of the location to check
     * @param y     The y coordinate of the location to check
     * @return True if the location is valid for the given mover
     */
    protected boolean isValidLocation(Mover mover, int sx, int sy, int sz, int x, int y, int z) {
        boolean invalid = (x < 0) || (y < 0) || (z < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles()) || (z >= map.getDepthInTiles());

        if ((!invalid) && ((sx != x) || (sy != y))) {
            invalid = map.blocked(mover,nodes[x][ y][ z]);
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
    public float getMovementCost(Mover mover, int sx, int sy, int sz, int tx, int ty, int tz) {
        return map.getCost(mover, sx, sy, sz, tx, ty, tz);
    }

    /**
     * Get the heuristic cost for the given location. This determines in which
     * order the locations are processed.
     *
     * @param mover The entity that is being moved
     * @param x     The x coordinate of the tile whose cost is being determined
     * @param y     The y coordinate of the tile whose cost is being determined
     * @param z     The z coordinate of the tile whose cost is being determined
     * @param tx    The x coordinate of the target location
     * @param ty    The y coordinate of the target location
     * @param tz    The z coordinate of the target location
     * @return The heuristic cost assigned to the tile
     */
    public float getHeuristicCost(Mover mover, int x, int y, int z, int tx, int ty, int tz) {
        return heuristic.getCost(map, mover, x, y, z, tx, ty, tz);
    }


}