package com.stewsters.util.pathing.twoDimention.hpa;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.CanOccupy2d;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

public class ChunkPathfinder {
    private PathNode2d[][] nodes;

    public ChunkPathfinder(int xSize, int ySize) {
        nodes = new PathNode2d[xSize][ySize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                nodes[x][y] = new PathNode2d(x, y);
            }
        }
    }

    public ArrayList<Point2i> getPath(CanTraverse2d canTraverse2d, CanOccupy2d canOccupy2d, MovementCost2d movementCost2d, Chunk2d chunk, int sx, int sy, int tx, int ty, AStarHeuristic2d heuristic, float maxSearchDistance, boolean allowDiag) {

        if (heuristic == null)
            return null;

        if (!canOccupy2d.canOccupy(tx, ty))
            return null;

        PriorityQueue<PathNode2d> open = new PriorityQueue<>();

        nodes[sx][sy].cost = 0;
        nodes[sx][sy].depth = 0;
        open.add(nodes[sx][sy]);
        nodes[tx][ty].parent = null;

        int maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {
            // pull out the first PathNode in our open list, this is determined to
            // be the most likely to be the next step based on our heuristic

            PathNode2d current = open.poll();
            if (current == nodes[tx][ty]) {
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

                    if (!allowDiag) {
                        if ((x != 0) && (y != 0)) {
                            continue;
                        }
                    }

                    // determine the location of the neighbour and evaluate it
                    int xp = x + current.x;
                    int yp = y + current.y;

                    if (xp < 0 || yp < 0 || xp >= chunk.getXSize() || yp >= chunk.getYSize())
                        continue;

                    if (!canTraverse2d.canTraverse(sx, sy, xp, yp))
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
                        if (neighbour.closed) {
                            neighbour.closed = false;
                        }
                    }

                    // if the PathNode hasn't already been processed and discarded then
                    // reset it's cost to our current cost and add it as a next possible
                    // step (i.e. to the open list)

                    if (!open.contains(neighbour) && !neighbour.closed) {
                        neighbour.cost = nextStepCost;
                        neighbour.heuristic = heuristic.getCost(chunk, xp, yp, tx, ty);
                        maxDepth = Math.max(maxDepth, neighbour.setParent(current));
                        open.add(neighbour);
                    }
                }
            }

        }

        // since we've run out of search there was no path. Just return null
        if (nodes[tx][ty].parent == null) {
            return null;
        }

        /*
         At this point we've definitely found a path so we can uses the parent
         references of the nodes to find out way from the target location back
         to the start recording the nodes on the way.
        */
        ArrayList<Point2i> path = new ArrayList<>();

        PathNode2d target = nodes[tx][ty];
        while (target != nodes[sx][sy]) {
            path.add(new Point2i(target.x, target.y));
            target = target.parent;
        }
        path.add(new Point2i(sx, sy));
        Collections.reverse(path);

        // That's it, we have our path
        return path;
    }
}
