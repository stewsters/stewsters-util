package com.stewsters.util.pathing.threeDimention.pathfinder;


import com.stewsters.util.pathing.threeDimention.shared.Mover3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

/**
 * A heuristic that uses manhattan distance
 *
 * @author Adrian Moore
 */
public class ManhattanHeuristic3d implements AStarHeuristic3d {

    public float getCost(TileBasedMap3d map, int x, int y, int z, int tx, int ty, int tz) {

        int dx = Math.abs(tx - x);
        int dy = Math.abs(ty - y);
        int dz = Math.abs(tz - z);

        return dx + dy + dz;
    }

}