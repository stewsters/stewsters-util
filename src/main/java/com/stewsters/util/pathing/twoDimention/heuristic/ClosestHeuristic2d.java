package com.stewsters.util.pathing.twoDimention.heuristic;

import com.stewsters.util.pathing.twoDimention.shared.BoundingBox2d;

/**
 * A heuristic that uses the Pythagorean Theorem to find distance.
 */
public class ClosestHeuristic2d implements AStarHeuristic2d {

    public float getCost(BoundingBox2d map, int x, int y, int tx, int ty) {
        float dx = tx - x;
        float dy = ty - y;

        return (float) (Math.sqrt((dx * dx) + (dy * dy)));
    }

}
