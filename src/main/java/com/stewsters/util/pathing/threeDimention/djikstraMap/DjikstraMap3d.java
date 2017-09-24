package com.stewsters.util.pathing.threeDimention.djikstraMap;

import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

import java.util.PriorityQueue;

public class DjikstraMap3d implements PathingMap3d {

    // The set of nodes that we do not yet consider fully searched
    private PriorityQueue<PathNode3d> open = new PriorityQueue<>();

    // The map being searched
    private TileBasedMap3d map;

    // The maximum depth of search we're willing to accept before giving up
    private int maxSearchDistance;

    // The complete set of nodes across the map
    private PathNode3d[][][] nodes;


    public DjikstraMap3d(TileBasedMap3d map, int maxSearchDistance) {
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
    public void recalculate(
            CanTraverse3d canTraverse3d,
            MovementCost3d movementCost3d,
            boolean allowDiagMovement,
            int sX, int sY, int sZ) {
        reset();

        nodes[sX][sY][sZ].cost = 0;
        nodes[sX][sY][sZ].depth = 0;
        open.add(nodes[sX][sY][sZ]);

        // while we haven't exceeded our max search depth
        int maxDepth = 0;

        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {

            PathNode3d current = open.peek();

            open.remove(current);
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

                        if (!isValidLocation(canTraverse3d, current.x, current.y, current.z, xp, yp, zp))
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
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            open.add(neighbour);
                        }
                    }

                }
            }
        }

    }

    protected boolean isValidLocation(CanTraverse3d canTraverse3d, int sx, int sy, int sz, int tx, int ty, int tz) {
        if ((tx < 0) || (ty < 0) || (tz < 0) || (tx >= map.getXSize()) || (ty >= map.getYSize()) || (tz >= map.getZSize())) {
            return false;
        }
        return canTraverse3d.canTraverse(sx, sy, sz, tx, ty, tz);
    }

    public float getDistanceAt(int x, int y, int z) {
        if ((x < 0) || (y < 0) || (z < 0) || (x >= map.getXSize()) || (y >= map.getYSize()) || (z >= map.getZSize())) {
            return 0f;
        }
        return nodes[x][y][z].cost;
    }

}
