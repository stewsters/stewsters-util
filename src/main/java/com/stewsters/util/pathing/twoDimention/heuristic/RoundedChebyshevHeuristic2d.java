package com.stewsters.util.pathing.twoDimention.heuristic;

import com.stewsters.util.pathing.twoDimention.shared.BoundingBox2d;

/**
 * A heuristic that uses Chebyshev Distance, but with a sqrt of 2 as the diagonal cost
 *
 * @author Adrian Moore
 */
public class RoundedChebyshevHeuristic2d implements AStarHeuristic2d {
    /**
     * @see AStarHeuristic2d#getCost(BoundingBox2d, int, int, int, int)
     */
    public float getCost(BoundingBox2d map, int x, int y, int tx, int ty) {

        int dx = Math.abs(tx - x);
        int dy = Math.abs(ty - y);

        return (0.70710678118f * Math.max(dx, dy)) +
                (0.29289321881f * Math.min(dx, dy));
    }

}