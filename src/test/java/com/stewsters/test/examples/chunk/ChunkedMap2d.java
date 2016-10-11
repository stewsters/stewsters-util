package com.stewsters.test.examples.chunk;

/**
 * Map we are pathfinding over
 */
public interface ChunkedMap2d {

    int getXSize();

    int getYSize();


    boolean isOutsideMap(int tx, int ty);
}
