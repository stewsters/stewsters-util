package com.stewsters.util.pathing.twoDimention.pathfinder;


import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 * A heuristic that uses manhattan distance
 *
 * @author Adrian Moore
 */
public class ManhattanHeuristic2d implements AStarHeuristic2d {

    public float getCost(TileBasedMap2d map, int x, int y, int tx, int ty) {

        int dx = Math.abs(tx - x);
        int dy = Math.abs(ty - y);

        return dx + dy;
    }

}