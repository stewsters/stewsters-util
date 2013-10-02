package com.stewsters.util.pathing.threeDimention.pathfinder;

import com.stewsters.util.pathing.threeDimention.shared.Mover;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 *
 * @author Kevin Glass
 * @author Adrian Moore
 */
public class ClosestHeuristic implements AStarHeuristic {
    /**
     * @see AStarHeuristic#getCost(com.stewsters.util.pathing.threeDimention.shared.TileBasedMap, com.stewsters.util.pathing.threeDimention.shared.Mover, int, int, int, int, int, int)
     */
    public float getCost(TileBasedMap map, Mover mover, int x, int y, int z, int tx, int ty, int tz) {

        float dx = tx - x;
        float dy = ty - y;
        float dz = ty - z;

        float result = (float) (Math.sqrt((dx * dx) + (dy * dy) + (dz * dz)));

        return result;
    }

}