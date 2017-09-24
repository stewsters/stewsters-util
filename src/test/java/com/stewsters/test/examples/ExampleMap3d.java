package com.stewsters.test.examples;

import com.stewsters.util.pathing.threeDimention.shared.BoundingBox3d;

public class ExampleMap3d implements BoundingBox3d {

    private final int xSize;
    private final int ySize;
    private final int zSize;
    public ExampleCellType ground[][][];

    public ExampleMap3d(int xSize, int ySize, int zSize, ExampleCellType baseType) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;

        ground = new ExampleCellType[xSize][ySize][zSize];
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    ground[x][y][z] = baseType;
                }
            }
        }

    }

    public void setTile(int x, int y, int z, ExampleCellType baseType) {
        ground[x][y][z] = baseType;
    }

    @Override
    public int getXSize() {
        return xSize;
    }

    @Override
    public int getYSize() {
        return ySize;
    }

    @Override
    public int getZSize() {
        return zSize;
    }

    public boolean isBlocked(int x, int y, int z) {
        if (x < 0 || x >= getXSize() || y < 0 || y >= getYSize() || z < 0 || z >= getZSize())
            return false;
        return ground[x][y][z].isBlocking();
    }

}
