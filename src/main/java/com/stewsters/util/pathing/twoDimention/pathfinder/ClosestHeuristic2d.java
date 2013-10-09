package com.stewsters.util.pathing.twoDimention.pathfinder;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 * A heuristic that uses the tile that is closest to the target
 * as the next best tile.
 *
 * @author Kevin Glass
 * @author Adrian Moore
 */
public class ClosestHeuristic2d implements AStarHeuristic2d {
    /**
     * @see AStarHeuristic2d#getCost(com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d, com.stewsters.util.pathing.twoDimention.shared.Mover2d, int, int, int, int)
     */
    public float getCost(TileBasedMap2d map, Mover2d mover, int x, int y, int tx, int ty) {

        float dx = tx - x;
        float dy = ty - y;

        float result = (float) (Math.sqrt((dx * dx) + (dy * dy)));

        return result;
    }

}