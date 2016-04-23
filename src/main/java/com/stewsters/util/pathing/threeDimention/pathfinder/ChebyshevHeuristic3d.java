package com.stewsters.util.pathing.threeDimention.pathfinder;


import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

/**
 * A heuristic that uses Chebyshev Distance
 *
 * @author Adrian Moore
 */
public class ChebyshevHeuristic3d implements AStarHeuristic3d {

    public float getCost(TileBasedMap3d map, int x, int y, int z, int tx, int ty, int tz) {

        int dx = Math.abs(tx - x);
        int dy = Math.abs(ty - y);
        int dz = Math.abs(tz - z);

        return Math.max(dz, Math.max(dx, dy));
    }

}
