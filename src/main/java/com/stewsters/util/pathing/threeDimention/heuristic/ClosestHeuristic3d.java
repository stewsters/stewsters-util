package com.stewsters.util.pathing.threeDimention.heuristic;

import com.stewsters.util.pathing.threeDimention.shared.BoundingBox3d;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 *
 * @author Kevin Glass
 * @author Adrian Moore
 */
public class ClosestHeuristic3d implements AStarHeuristic3d {

    public float getCost(BoundingBox3d map, int x, int y, int z, int tx, int ty, int tz) {

        float dx = tx - x;
        float dy = ty - y;
        float dz = ty - z;

        return (float) (Math.sqrt((dx * dx) + (dy * dy) + (dz * dz)));
    }

}