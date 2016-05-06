package com.stewsters.util.pathing.twoDimention.pathfinder;

import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 * A heuristic that uses the Pythagorean Theorem to find distance.
 *
 * @author Kevin Glass
 * @author Adrian Moore
 */
public class ClosestHeuristic2d implements AStarHeuristic2d {

    public float getCost(TileBasedMap2d map, int x, int y, int tx, int ty) {

        float dx = tx - x;
        float dy = ty - y;

        return (float) (Math.sqrt((dx * dx) + (dy * dy)));

    }

}
