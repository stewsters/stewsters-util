package com.stewsters.util.pathing.threeDimention.shared;

public interface MovementCost3d {
    float getCost(int sx, int sy, int sz, int tx, int ty, int tz);
}
