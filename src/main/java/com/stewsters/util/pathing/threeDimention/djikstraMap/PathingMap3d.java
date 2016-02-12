package com.stewsters.util.pathing.threeDimention.djikstraMap;

import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public interface PathingMap3d {

    void recalculate(int sX, int sY, int sZ, Mover3d mover);

    float getDistanceAt(int x, int y, int z);
}
