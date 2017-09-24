package com.stewsters.util.pathing.threeDimention.pathfinder;

import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.AStarHeuristic3d;
import com.stewsters.util.pathing.threeDimention.shared.BoundingBox3d;
import com.stewsters.util.pathing.threeDimention.shared.CanOccupy3d;
import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

/**
 * A path finder implementation that uses the AStar heuristic based algorithm
 * to determine a path.
 *
 * @author Adrian Moore
 * @author Kevin Glass
 */
public class AStarPathFinder3d implements PathFinder3d {
    /**
     * The set of nodes that we do not yet consider fully searched
     */
    private PriorityQueue<PathNode3d> open = new PriorityQueue<>();

    /**
     * The map being searched
     */
    private BoundingBox3d map;
    /**
     * The maximum depth of search we're willing to accept before giving up
     */
    private int maxSearchDistance;

    /**
     * The complete set of nodes across the map
     */
    private PathNode3d[][][] nodes;

    public AStarPathFinder3d(BoundingBox3d map, int maxSearchDistance) {
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;

        nodes = new PathNode3d[map.getXSize()][map.getYSize()][map.getZSize()];
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (int z = 0; z < map.getZSize(); z++) {
                    nodes[x][y][z] = new PathNode3d(x, y, z);
                }
            }
        }
    }

    public void reset() {
        open.clear();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (int z = 0; z < map.getZSize(); z++) {
                    nodes[x][y][z].closed = false;
                }
            }
        }
    }

    @Override
    public Optional<List<Point3i>> findPath(
            CanTraverse3d canTraverse3d,
            CanOccupy3d canOccupy3d,
            MovementCost3d movementCost3d,
            AStarHeuristic3d heuristic,
            boolean allowDiagMovement,
            int sx, int sy, int sz, int tx, int ty, int tz) {
        // easy first check, if the destination is blocked, we can't get there

        if (!canOccupy3d.canOccupy(tx, ty, tz)) {
            return Optional.empty();
        }

        reset();

        // initial state for A*. The closed group is empty. Only the starting
        // tile is in the open list and it's already there
        nodes[sx][sy][sz].cost = 0;
        nodes[sx][sy][sz].depth = 0;
        open.add(nodes[sx][sy][sz]);

        nodes[tx][ty][tz].parent = null;

        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode3d current = open.poll();
            if (current == nodes[tx][ty][tz]) {
                break;
            }

            current.closed = true;

            // search through all the neighbors of the current PathNode evaluating them as next steps
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
                            if (!((x == 0 && y == 0) || (y == 0 && z == 0) || (z == 0 && x == 0))) {
                                continue;
                            }
                        }

                        // determine the location of the neighbour and evaluate it

                        int xp = x + current.x;
                        int yp = y + current.y;
                        int zp = z + current.z;

                        if (!isValidLocation(canTraverse3d, sx, sy, sz, xp, yp, zp))
                            continue;
                        // the cost to get to this PathNode is cost the current plus the movement
                        // cost to reach this node. Note that the heuristic value is only used
                        // in the sorted open list

                        float nextStepCost = current.cost + movementCost3d.getCost(current.x, current.y, current.z, xp, yp, zp);
                        PathNode3d neighbour = nodes[xp][yp][zp];

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
                            neighbour.heuristic = heuristic.getCost(map, xp, yp, zp, tx, ty, tz);
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            open.add(neighbour);
                        }

                    }
                }
            }
        }

        // since we'e've run out of search
        // there was no path. Just return null
        if (nodes[tx][ty][tz].parent == null) {
            return Optional.empty();
        }

        // At this point we've definitely found a path so we can uses the parent  references of the nodes to find out
        // way from the target location back to the start recording the nodes on the way.
        List<Point3i> path = new ArrayList<>();
        PathNode3d target = nodes[tx][ty][tz];
        while (target != nodes[sx][sy][sz]) {
            path.add(new Point3i(target.x, target.y, target.z));
            target = target.parent;
        }
        path.add(new Point3i(sx, sy, sz));

        Collections.reverse(path);

        // thats it, we have our path
        return Optional.of(path);
    }

    protected boolean isValidLocation(CanTraverse3d canTraverse3d, int sx, int sy, int sz, int tx, int ty, int tz) {
        if ((tx < 0) || (ty < 0) || (tz < 0) || (tx >= map.getXSize()) || (ty >= map.getYSize()) || (tz >= map.getZSize())) {
            return false;
        }
        return canTraverse3d.canTraverse(sx, sy, sz, tx, ty, tz);
    }

}
