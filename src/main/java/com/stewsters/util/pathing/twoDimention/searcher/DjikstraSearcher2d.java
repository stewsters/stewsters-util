package com.stewsters.util.pathing.twoDimention.searcher;

import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

import java.util.PriorityQueue;

public class DjikstraSearcher2d implements Searcher2d {

    // The set of nodes that we do not yet consider fully searched
    private PriorityQueue<PathNode2d> open = new PriorityQueue<>();

    // The map being searched
    private TileBasedMap2d map;

    // The maximum depth of search we're willing to accept before giving up
    private int maxSearchDistance;

    // The complete set of nodes across the map
    private PathNode2d[][] nodes;

    // True if we allow diagonal movement
    private boolean allowDiagMovement;

    public DjikstraSearcher2d(TileBasedMap2d map, int maxSearchDistance, boolean allowDiagMovement) {
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
    public FullPath2d search(Mover2d mover, int sx, int sy, Objective2d objective) {
        reset();

        nodes[sx][sy].cost = 0;
        nodes[sx][sy].depth = 0;

        open.add(nodes[sx][sy]);

        // while we haven't exceeded our max search depth
        int maxDepth = 0;
        PathNode2d target = null;

        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode2d current = open.poll();


            if (objective.satisfiedBy(current)) {
                //TODO: this should set the answer location
                target = current;
                break;
            }

            current.closed = true;

            // search through all the neighbors of the current PathNode evaluating

            // them as next steps
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

                    if (isValidLocation(mover, sx, sy, xp, yp)) {
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
                            maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                            open.add(neighbour);
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
        FullPath2d path = new FullPath2d();
        while (target != nodes[sx][sy]) {
            path.appendStep(target.x, target.y);
            target = target.parent;
        }
        path.appendStep(sx, sy);
        path.reverse();

        // thats it, we have our path
        return path;
    }

    /**
     * Check if a given location is valid for the supplied mover
     *
     * @param mover The mover that would hold a given location
     * @param sx    The starting x coordinate
     * @param sy    The starting y coordinate
     * @param tx    The x coordinate of the location to check
     * @param ty    The y coordinate of the location to check
     * @return True if the location is valid for the given mover
     */
    protected boolean isValidLocation(Mover2d mover, int sx, int sy, int tx, int ty) {
        return !((tx < 0) || (ty < 0)
                || (tx >= map.getXSize()) || (ty >= map.getYSize()))
                && mover.canTraverse(sx, sy, tx, ty);
    }
}
