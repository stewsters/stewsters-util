package com.stewsters.util.pathing.twoDimention.shared;

public interface BoundingBox2d {

    int getXSize();

    int getYSize();

    default boolean contains(int x, int y){
        return x >= 0 && y >= 0 && x < getXSize() && y < getYSize();
    }

    default boolean isOutside(int x, int y){
        return x < 0 || y < 0 || x >= getXSize() || y >= getYSize();
    }
}