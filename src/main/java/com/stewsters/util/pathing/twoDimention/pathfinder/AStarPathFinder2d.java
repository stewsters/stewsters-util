package com.stewsters.util.pathing.twoDimention.pathfinder;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path.
 *
 * @author Kevin Glass
 */
public class AStarPathFinder2d implements PathFinder2d {
    /**
     * The set of nodes that have been searched through
     */
    private ArrayList<PathNode2d> closed = new ArrayList();
    /**
     * The set of nodes that we do not yet consider fully searched
     */
    private PriorityQueue<PathNode2d> open = new PriorityQueue<>();

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
    /**
     * The heuristic we're applying to determine which nodes to search first
     */
    private AStarHeuristic2d heuristic;

    /**
     * Create a path finder with the default heuristic - closest to target.
     *
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     * @param allowDiagMovement True if the search should try diagonal movement
     */
    public AStarPathFinder2d(TileBasedMap2d map, int maxSearchDistance, boolean allowDiagMovement) {
        this(map, maxSearchDistance, allowDiagMovement, (AStarHeuristic2d) new ClosestHeuristic2d());
    }

    /**
     * Create a path finder
     *
     * @param heuristic         The heuristic used to determine the search order of the map
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     * @param allowDiagMovement True if the search should try diaganol movement
     */
    public AStarPathFinder2d(TileBasedMap2d map, int maxSearchDistance,
                             boolean allowDiagMovement, AStarHeuristic2d heuristic) {
        this.heuristic = heuristic;
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

    /**
     * @see PathFinder2d#findPath(com.stewsters.util.pathing.twoDimention.shared.Mover2d, int, int, int, int)
     */
    public FullPath2d findPath(Mover2d mover, int sx, int sy, int tx, int ty) {
        // easy first check, if the destination is blocked, we can't get there

        if (map.isBlocked(mover, nodes[tx][ty])) {
            return null;
        }

        // initial state for A*. The closed group is empty. Only the starting
        // tile is in the open list and it's already there
        nodes[sx][sy].cost = 0;
        nodes[sx][sy].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[sx][sy]);

        nodes[tx][ty].parent = null;

        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode2d current = getFirstInOpen();
            if (current == nodes[tx][ty]) {
                break;
            }

            removeFromOpen(current);
            addToClosed(current);

            // search through all the neighbours of the current PathNode evaluating

            // them as next steps

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    // not a neighbour, its the current tile

                    if ((x == 0) && (y == 0)) {
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

                    if (isValidLocation(mover, sx, sy, xp, yp)) {
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
                            neighbour.heuristic = getHeuristicCost(mover, xp, yp, tx, ty);
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            addToOpen(neighbour);
                        }
                    }
                }
            }

        }

        // since we'e've run out of search
        // there was no path. Just return null

        if (nodes[tx][ty].parent == null) {
            return null;
        }

        // At this point we've definitely found a path so we can uses the parent

        // references of the nodes to find out way from the target location back

        // to the start recording the nodes on the way.

        FullPath2d path = new FullPath2d();
        PathNode2d target = nodes[tx][ty];
        while (target != nodes[sx][sy]) {
            path.prependStep(target.x, target.y);
            target = target.parent;
        }
        path.prependStep(sx, sy);

        // thats it, we have our path

        return path;
    }

    /**
     * Get the first element from the open list. This is the next
     * one to be searched.
     *
     * @return The first element in the open list
     */
    protected PathNode2d getFirstInOpen() {
        return open.peek();
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
     * @param sx    The starting x coordinate
     * @param sy    The starting y coordinate
     * @param x     The x coordinate of the location to check
     * @param y     The y coordinate of the location to check
     * @return True if the location is valid for the given mover
     */
    protected boolean isValidLocation(Mover2d mover, int sx, int sy, int x, int y) {
        boolean invalid = (x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles());

        if ((!invalid) && ((sx != x) || (sy != y))) {
            invalid = map.isBlocked(mover, nodes[x][y]);
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

    /**
     * Get the heuristic cost for the given location. This determines in which
     * order the locations are processed.
     *
     * @param mover The entity that is being moved
     * @param x     The x coordinate of the tile whose cost is being determined
     * @param y     The y coordinate of the tile whose cost is being determined
     * @param tx    The x coordinate of the target location
     * @param ty    The y coordinate of the target location
     * @return The heuristic cost assigned to the tile
     */
    public float getHeuristicCost(Mover2d mover, int x, int y, int tx, int ty) {
        return heuristic.getCost(map, mover, x, y, tx, ty);
    }

    /**
     * Find all locations within a radius that are reachable by the mover
     *
     * @param mover The entity that is being moved
     * @param sx    The x coordinate of the tile whose cost is being determined
     * @param sy    The y coordinate of the tile whose cost is being determined
     * @param max   The Max cost
     */
    public LinkedList<Point2i> getReachableCells(Mover2d mover, int sx, int sy, float max) {

        LinkedList<Point2i> reachableCells = new LinkedList<Point2i>();
        nodes[sx][sy].cost = 0;
        nodes[sx][sy].depth = 0;
        closed.clear();
        open.clear();
        open.add(nodes[sx][sy]);

        while (open.size() > 0) {
            // poll() the open queue
            PathNode2d current = open.poll();

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {

                    int nx = current.x + x;
                    int ny = current.y + y;

                    if ((x == 0) && (y == 0))
                        continue;

                    if (nx < 0 || ny < 0 || nx >= nodes.length || ny >= nodes[0].length)
                        continue;

                    if (!allowDiagMovement) {
                        if ((x != 0) && (y != 0)) {
                            continue;
                        }
                    }

                    if (!isValidLocation(mover, current.x, current.y, nx, ny))
                        continue;

                    float nextStepCost = current.cost + getMovementCost(mover, current.x, current.y, nx, ny);
                    PathNode2d neighbor = nodes[nx][ny];

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

                        Point2i point = new Point2i(neighbor.x, neighbor.y);
                        if (!reachableCells.contains(point))
                            reachableCells.push(point);
                    }
                }
            }
            addToClosed(current);
        }

        return reachableCells;

    }

}