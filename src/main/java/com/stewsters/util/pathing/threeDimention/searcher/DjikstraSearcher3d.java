package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class DjikstraSearcher3d implements Searcher3d {

    // The set of nodes that we do not yet consider fully searched
    private PriorityQueue<PathNode3d> open = new PriorityQueue<>();

    // The map being searched
    private TileBasedMap3d map;

    // The maximum depth of search we're willing to accept before giving up
    private int maxSearchDistance;

    // The complete set of nodes across the map
    private PathNode3d[][][] nodes;

    public DjikstraSearcher3d(TileBasedMap3d map, int maxSearchDistance) {
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
    public Optional<List<Point3i>> search(
            Objective3d objective,
            CanTraverse3d canTraverse3d,
            MovementCost3d movementCost3d,
            boolean allowDiagMovement,
            int sx, int sy, int sz) {

        reset();

        nodes[sx][sy][sz].cost = 0;
        nodes[sx][sy][sz].depth = 0;

        open.add(nodes[sx][sy][sz]);

        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        PathNode3d target = null;

        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode3d current = open.poll();

            if (objective.satisfiedBy(current)) {
                target = current;
                break;
            }

            current.closed = true;

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

                        if (!isValidLocation(canTraverse3d, sx, sy, sz, xp, yp, zp)) {
                            continue;
                        }

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
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            open.add(neighbour);
                        }
                    }
                }

            }
        }

        // since we'e've run out of search, there was no path. Just return null
        if (target == null) {
            return Optional.empty();
        }

        /* At this point we've definitely found a path so we can use the parent
         * references of the nodes to find out way from the target location back
         * to the start recording the nodes on the way.
        */

        List<Point3i> path = new ArrayList<>();
        while (target != nodes[sx][sy][sz]) {
            path.add(new Point3i(target.x, target.y, target.z));
            target = target.parent;
        }
        path.add(new Point3i(sx, sy, sz));
        Collections.reverse(path);

        // thats it, we have our path
        return Optional.of(path);
    }


    /**
     * Check if a given location is valid for the supplied mover
     *
     * @param mover The mover that would hold a given location
     * @param sx    The starting x coordinate
     * @param sy    The starting y coordinate
     * @param sz    The starting z coordinate
     * @param tx    The x coordinate of the location to check
     * @param ty    The y coordinate of the location to check
     * @param tz    The z coordinate of the location to check
     * @return True if the location is valid for the given mover
     */
    protected boolean isValidLocation(CanTraverse3d mover, int sx, int sy, int sz, int tx, int ty, int tz) {
        return !((tx < 0) || (ty < 0) || (tz < 0)
                || (tx >= map.getXSize()) || (ty >= map.getYSize()) || (tz >= map.getZSize()))
                && mover.canTraverse(sx, sy, sz, tx, ty, tz);
    }
}
