package com.stewsters.util.pathing.twoDimention.djikstraMap;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

import java.util.PriorityQueue;

public class DjikstraMap2d implements PathingMap2d {

    // The set of nodes that we do not yet consider fully searched
    private PriorityQueue<PathNode2d> open = new PriorityQueue<>();

    // The map being searched
    private TileBasedMap2d map;

    // The maximum depth of search we're willing to accept before giving up
    private int maxSearchDistance;

    // The complete set of nodes across the map
    private PathNode2d[][] nodes;

    /**
     * True if we allow diagonal movement
     */
    private boolean allowDiagMovement;

    public DjikstraMap2d(TileBasedMap2d map, int maxSearchDistance, boolean allowDiagMovement) {
        this.map = map;
        this.maxSearchDistance = maxSearchDistance;
        this.allowDiagMovement = allowDiagMovement;

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
    public void recalculate(int sX, int sY, Mover2d mover) {
        reset();

        nodes[sX][sY].cost = 0;
        nodes[sX][sY].depth = 0;
        nodes[sX][sY].parent = null;
        open.add(nodes[sX][sY]);

        // while we haven't exceeded our max search depth
        int maxDepth = 0;

        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {

            PathNode2d current = open.peek();

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
                            if ((x != 0) && (y != 0)) {
                                continue;
                            }
                        }

                        // determine the location of the neighbour and evaluate it

                        int xp = x + current.x;
                        int yp = y + current.y;

                        if (isValidLocation(mover, current.x, current.y, xp, yp)) {
                            // the cost to get to this PathNode is cost the current plus the movement
                            // cost to reach this node. Note that the heuristic value is only used
                            // in the sorted open list

                            float nextStepCost = current.cost + mover.getCost(current.x, current.y, xp, yp);
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
                                neighbour.heuristic = 0; //getHeuristicCost(mover, xp, yp, zp, tx, ty, tz);
                                maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                                open.add(neighbour);
                            }
                        }
                    }
                }
            }
        }

    }

    protected boolean isValidLocation(Mover2d mover, int sx, int sy, int tx, int ty) {
        if ((tx < 0) || (ty < 0) || (tx >= map.getXSize()) || (ty >= map.getYSize())) {
            return false;
        }
        return mover.canTraverse(sx, sy, tx, ty);
    }

    public float getDistanceAt(int x, int y) {
        if ((x < 0) || (y < 0) || (x >= map.getXSize()) || (y >= map.getYSize())) {
            return 0f;
        }
        return nodes[x][y].cost;
    }

}
