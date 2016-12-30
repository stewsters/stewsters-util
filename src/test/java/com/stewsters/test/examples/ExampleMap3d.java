package com.stewsters.test.examples;

import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

/**
 *
 */
public class ExampleMap3d implements TileBasedMap3d {

    private final int width;
    private final int height;
    private final int depth;
    private ExampleCellType ground[][][];

    public ExampleMap3d(int width, int height, int depth, ExampleCellType baseType) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        ground = new ExampleCellType[width][height][depth];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    ground[x][y][z] = baseType;
                }
            }
        }

        //set up some walls

    }

    public void setTile(int x, int y, int z, ExampleCellType baseType) {
        ground[x][y][z] = baseType;
    }

    @Override
    public int getXSize() {
        return width;
    }

    @Override
    public int getYSize() {
        return height;
    }

    @Override
    public int getZSize() {
        return depth;
    }

    public boolean isBlocked(int x, int y, int z) {
        if (x < 0 || x >= getXSize() || y < 0 || y >= getYSize() || z < 0 || z >= getZSize())
            return false;
        return ground[x][y][z].isBlocking();
    }


}
