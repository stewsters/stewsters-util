package com.stewsters.util.pathing.twoDimention.pathfinder

import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d
import com.stewsters.util.pathing.twoDimention.shared.Mover2d
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d

/**
 * A heuristic that uses manhattan distance
 *
 * @author Adrian Moore
 */
public class RoundedChebyshevHeuristic2d implements AStarHeuristic2d {
    /**
     * @see com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d#getCost(com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d, com.stewsters.util.pathing.twoDimention.shared.Mover2d, int, int, int, int)
     */
    public float getCost(TileBasedMap2d map, Mover2d mover, int x, int y, int tx, int ty) {

        int dx = Math.abs(tx - x);
        int dy = Math.abs(ty - y);

        return Math.max(dx, dy) + (0.41421356237f) * Math.min(dx, dy);
    }

}