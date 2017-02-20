package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 *
 */
public class ExampleMap2d implements TileBasedMap2d {

    private final int xSize;
    private final int ySize;
    public ExampleCellType[][] ground;

    public ExampleMap2d(int xSize, int ySize, ExampleCellType baseType) {
        this.xSize = xSize;
        this.ySize = ySize;

        ground = new ExampleCellType[xSize][ySize];

        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {

                ground[x][y] = baseType;
            }
        }

    }

    @Override
    public int getXSize() {
        return xSize;
    }

    @Override
    public int getYSize() {
        return ySize;
    }

    public boolean isBlocked(int x, int y) {
        if (x < 0 || x >= getXSize() || y < 0 || y >= getYSize())
            return false;

        return ground[x][y].isBlocking();
    }

    @Override
    public boolean isOutside(int x, int y) {
        return x < 0 || y < 0 || x >= xSize || y >= ySize ;
    }

}
