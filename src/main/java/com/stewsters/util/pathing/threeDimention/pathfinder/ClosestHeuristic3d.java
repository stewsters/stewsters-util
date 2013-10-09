package com.stewsters.util.pathing.threeDimention.pathfinder;

import com.stewsters.util.pathing.threeDimention.shared.Mover3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 *
 * @author Kevin Glass
 * @author Adrian Moore
 */
public class ClosestHeuristic3d implements AStarHeuristic3d {
    /**
     * @see AStarHeuristic3d#getCost(com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d, com.stewsters.util.pathing.threeDimention.shared.Mover3d, int, int, int, int, int, int)
     */
    public float getCost(TileBasedMap3d map, Mover3d mover, int x, int y, int z, int tx, int ty, int tz) {

        float dx = tx - x;
        float dy = ty - y;
        float dz = ty - z;

        float result = (float) (Math.sqrt((dx * dx) + (dy * dy) + (dz * dz)));

        return result;
    }

}