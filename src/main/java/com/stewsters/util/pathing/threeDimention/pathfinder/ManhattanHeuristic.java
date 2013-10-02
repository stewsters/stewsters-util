package com.stewsters.util.pathing.threeDimention.pathfinder;


import com.stewsters.util.pathing.threeDimention.shared.Mover;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap;

/**
 * A heuristic that uses manhattan distance
 *
 * @author Adrian Moore
 */
public class ManhattanHeuristic implements AStarHeuristic {
    /**
     * @see AStarHeuristic#getCost(com.stewsters.util.pathing.threeDimention.shared.TileBasedMap, com.stewsters.util.pathing.threeDimention.shared.Mover, int, int, int, int, int, int)
     */
    public float getCost(TileBasedMap map, Mover mover, int x, int y, int z, int tx, int ty, int tz) {

        int dx = Math.abs(tx - x);
        int dy = Math.abs(ty - y);
        int dz = Math.abs(tz - z);

        return dx + dy + dz;
    }

}