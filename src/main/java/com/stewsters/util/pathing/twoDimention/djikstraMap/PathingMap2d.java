package com.stewsters.util.pathing.twoDimention.djikstraMap;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;

import java.util.Map;

public interface PathingMap2d {
    public void recalculate(Map<PathNode2d, Float> sourceTiles, Mover2d mover);

    public PathNode2d[][] getNodes();

    public float getCostAt(int x, int y);
}
