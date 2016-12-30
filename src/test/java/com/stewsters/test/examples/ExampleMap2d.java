package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 *
 */
public class ExampleMap2d implements TileBasedMap2d {

    private final int width;
    private final int height;
    public ExampleCellType[][] ground;

    public ExampleMap2d(int width, int height, ExampleCellType baseType) {
        this.width = width;
        this.height = height;

        ground = new ExampleCellType[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                ground[x][y] = baseType;
            }
        }

    }

    @Override
    public int getXSize() {
        return width;
    }

    @Override
    public int getYSize() {
        return height;
    }

    public boolean isBlocked(int x, int y) {
        if (x < 0 || x >= getXSize() || y < 0 || y >= getYSize())
            return false;

        return ground[x][y].isBlocking();
    }

}
