package com.stewsters.util.pathing.threeDimention.pathfinder;

import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path.
 *
 * @author Kevin Glass
 */
public class AStarPathFinder3d implements PathFinder3d {
    /**
     * The set of nodes that have been searched through
     */
    private ArrayList<PathNode3d> closed = new ArrayList<>();
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
     * True if we allow diaganol movement
     */
    private boolean allowDiagMovement;
    /**
     * The heuristic we're applying to determine which nodes to search first
     */
    private AStarHeuristic3d heuristic;

    /**
     * Create a path finder with the default heuristic - closest to target.
     *
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     * @param allowDiagMovement True if the search should try diagonal movement
     */
    public AStarPathFinder3d(TileBasedMap3d map, int maxSearchDistance, boolean allowDiagMovement) {
        this(map, maxSearchDistance, allowDiagMovement, (AStarHeuristic3d) new ClosestHeuristic3d());
    }

    /**
     * Create a path finder
     *
     * @param heuristic         The heuristic used to determine the search order of the map
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     * @param allowDiagMovement True if the search should try diaganol movement
     */
    public AStarPathFinder3d(TileBasedMap3d map, int maxSearchDistance,
                             boolean allowDiagMovement, AStarHeuristic3d heuristic) {
        this.heuristic = heuristic;
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

    protected boolean isBlocked(Mover3d mover, int x, int y, int z) {
        return map.isBlocked(mover, nodes[x][y][z]);
    }

    /**
     * @see PathFinder3d#findPath(com.stewsters.util.pathing.threeDimention.shared.Mover3d, int, int, int, int, int, int)
     */
    public FullPath3d findPath(Mover3d mover, int sx, int sy, int sz, int tx, int ty, int tz) {
        // easy first check, if the destination is blocked, we can't get there

        if (map.isBlocked(mover, tx, ty, tz)) {
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

            PathNode3d current = getFirstInOpen();
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
                            if (!((x == 0 && y == 0) || (y == 0 && z == 0) || (z == 0 && x == 0))) {
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
//            throw new RuntimeException("out of nodes");
            return null;
        }

        // At this point we've definitely found a path so we can uses the parent

        // references of the nodes to find out way from the target location back

        // to the start recording the nodes on the way.

        FullPath3d path = new FullPath3d();
        PathNode3d target = nodes[tx][ty][tz];
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

        if ((!invalid) && ((sx != x) || (sy != y) || (sz != z))) {
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
     * @param sz    The z coordiante of the tile whose cost is being determined
     * @param tx    The x coordinate of the target location
     * @param ty    The y coordinate of the target location
     * @param tz    The z coordinate of the target location
     * @return The cost of movement through the given tile
     */
    public float getMovementCost(Mover3d mover, int sx, int sy, int sz, int tx, int ty, int tz) {
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
    public float getHeuristicCost(Mover3d mover, int x, int y, int z, int tx, int ty, int tz) {
        return heuristic.getCost(map, mover, x, y, z, tx, ty, tz);
    }

    /**
     * Find all locations within a radius that are reachable by the mover
     *
     * @param mover The entity that is being moved
     * @param sx    The x coordinate of the tile whose cost is being determined
     * @param sy    The y coordinate of the tile whose cost is being determined
     * @param sz    The z coordinate of the tile whose cost is being determined
     * @param max   The Max cost
     * @return A list of reachable cells
     */
    public LinkedList<Point3i> getReachableCells(Mover3d mover, int sx, int sy, int sz, float max) {

        LinkedList<Point3i> reachableCells = new LinkedList<Point3i>();
        nodes[sx][sy][sz].cost = 0;
        nodes[sx][sy][sz].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[sx][sy][sz]);

        while (open.size() > 0) {
            // poll() the open queue
            PathNode3d current = open.poll();

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    for (int z = -1; z < 2; z++) {


                        int nx = current.x + x;
                        int ny = current.y + y;
                        int nz = current.z + z;

                        if ((x == 0) && (y == 0) && (z == 0))
                            continue;

                        if (nx < 0 || ny < 0 || nz < 0 || nx >= nodes.length || ny >= nodes[0].length || nz >= nodes[0][0].length)
                            continue;

                        if (!allowDiagMovement) {
                            if (!((x == 0 && y == 0) || (y == 0 && z == 0) || (z == 0 && x == 0))) {
                                continue;
                            }
                        }

                        if (!isValidLocation(mover, current.x, current.y, current.z, nx, ny, nz))
                            continue;

                        float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, current.z, nx, ny, nz);
                        PathNode3d neighbor = nodes[nx][ny][nz];

                        if (nextStepCost > max)
                            continue;

                        // Check to see if we have found a new shortest route to this neighbor, in
                        // which case it must be totally reconsidered
                        if (nextStepCost < neighbor.cost) {
                            if (inClosedList(neighbor)) removeFromClosed(neighbor);
                            if (open.contains(neighbor)) open.remove(neighbor);
                        }

                        if (!open.contains(neighbor) && !inClosedList(neighbor)) {
                            neighbor.cost = nextStepCost;
                            open.add(neighbor);

                            Point3i point = new Point3i(neighbor.x, neighbor.y, neighbor.z);
                            if (!reachableCells.contains(point))
                                reachableCells.push(point);
                        }
                    }
                }
            }
            addToClosed(current);
        }

        return reachableCells;

    }
}