package com.stewsters.util.pathing.threeDimention.shared;


public interface BoundingBox3d {

    int getXSize();

    int getYSize();

    int getZSize();

    default boolean contains(int x, int y, int z) {
        return x >= 0 && y >= 0 && z >= 0 && x < getXSize() && y < getYSize() && z < getZSize();
    }

    default boolean isOutside(int x, int y, int z) {
        return x < 0 || y < 0 || z < 0 || x >= getXSize() || y >= getYSize() || z >= getZSize();
    }

}
