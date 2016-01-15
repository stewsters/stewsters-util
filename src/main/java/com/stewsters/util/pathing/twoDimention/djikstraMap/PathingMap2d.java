package com.stewsters.util.pathing.twoDimention.djikstraMap;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

public interface PathingMap2d {

    void recalculate(int sX, int sY, Mover2d mover);

    float getDistanceAt(int x, int y);
}
