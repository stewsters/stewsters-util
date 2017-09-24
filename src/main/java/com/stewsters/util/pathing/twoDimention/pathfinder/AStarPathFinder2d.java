package com.stewsters.util.pathing.twoDimention.pathfinder;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.BoundingBox2d;
import com.stewsters.util.pathing.twoDimention.shared.CanOccupy2d;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path.
 *
 * @author Kevin Glass
 */
public class AStarPathFinder2d implements PathFinder2d {

    // The set of nodes that we do not yet consider fully searched
    private PriorityQueue<PathNode2d> open = new PriorityQueue<>();

    // The map being searched
    private BoundingBox2d map;

    // The maximum depth of search we're willing to accept before giving up
    private int maxSearchDistance;

    // The complete set of nodes across the map
    private PathNode2d[][] nodes;

    /**
     * Create a path finder
     *
     * @param map               The map to be searched
     * @param maxSearchDistance The maximum depth we'll search before giving up
     */
    public AStarPathFinder2d(BoundingBox2d map, int maxSearchDistance) {

        this.map = map;
        this.maxSearchDistance = maxSearchDistance;

        nodes = new PathNode2d[map.getXSize()][map.getYSize()];
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                nodes[x][y] = new PathNode2d(x, y);
            }
        }
    }

    public void reset() {
        open.clear();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                nodes[x][y].closed = false;
            }
        }
    }

    @Override
    public Optional<List<Point2i>> findPath(
            CanTraverse2d canTraverse2d,
            CanOccupy2d canOccupy2d,
            MovementCost2d movementCost2d,
            AStarHeuristic2d heuristic,
            boolean allowDiagMovement,
            int sx, int sy, int tx, int ty) {
        // easy first check, if the destination is blocked, we can't get there

        if (!canOccupy2d.canOccupy(tx, ty)) {
            return Optional.empty();
        }

        reset();

        // initial state for A*. The closed group is empty. Only the starting
        // tile is in the open list and it's already there
        nodes[sx][sy].cost = 0;
        nodes[sx][sy].depth = 0;
        open.add(nodes[sx][sy]);

        nodes[tx][ty].parent = null;

        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode2d current = open.poll();
            if (current == nodes[tx][ty]) {
                break;
            }

            current.closed = true;

            // search through all the neighbors of the current PathNode evaluating them as next steps
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    // not a neighbour, its the current tile

                    if ((x == 0) && (y == 0)) {
                        continue;
                    }

                    // if we're not allowing diagonal movement then only
                    // one of x or y can be set

                    if (!allowDiagMovement) {
                        if ((x != 0) && (y != 0)) {
                            continue;
                        }
                    }

                    // determine the location of the neighbour and evaluate it

                    int xp = x + current.x;
                    int yp = y + current.y;

                    if (!isValidLocation(canTraverse2d, sx, sy, xp, yp))
                        continue;

                    // the cost to get to this PathNode is cost the current plus the movement
                    // cost to reach this node. Note that the heuristic value is only used
                    // in the sorted open list

                    float nextStepCost = current.cost + movementCost2d.getCost(current.x, current.y, xp, yp);
                    PathNode2d neighbour = nodes[xp][yp];

                    // if the new cost we've determined for this PathNode is lower than
                    // it has been previously,
                    // there might have been a better path to get to
                    // this PathNode so it needs to be re-evaluated

                    if (nextStepCost < neighbour.cost) {
                        if (open.contains(neighbour)) {
                            open.remove(neighbour);
                        }
                        neighbour.closed = false;
                    }

                    // if the PathNode hasn't already been processed and discarded then
                    // reset it's cost to our current cost and add it as a next possible
                    // step (i.e. to the open list)

                    if (!open.contains(neighbour) && !neighbour.closed) {
                        neighbour.cost = nextStepCost;
                        neighbour.heuristic = heuristic.getCost(map, xp, yp, tx, ty);
                        maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                        open.add(neighbour);
                    }

                }
            }

        }

        // since we've run out of search there was no path. Just return null
        if (nodes[tx][ty].parent == null) {
            return Optional.empty();
        }

        /*
         At this point we've definitely found a path so we can uses the parent
         references of the nodes to find out way from the target location back
         to the start recording the nodes on the way.
        */

        List<Point2i> path = new ArrayList<>();
        PathNode2d target = nodes[tx][ty];
        while (target != nodes[sx][sy]) {
            path.add(new Point2i(target.x, target.y));
            target = target.parent;
        }
        path.add(new Point2i(sx, sy));
        Collections.reverse(path);

        // That's it, we have our path
        return Optional.of(path);
    }

    /**
     * Check if a given location is valid for the supplied mover
     */
    protected boolean isValidLocation(CanTraverse2d canTraverse2d, int sx, int sy, int tx, int ty) {
        if ((tx < 0) || (ty < 0) || (tx >= map.getXSize()) || (ty >= map.getYSize())) {
            return false;
        }
        return canTraverse2d.canTraverse(sx, sy, tx, ty);
    }

}
