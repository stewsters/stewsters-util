package com.stewsters.test.examples;

import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public class ExampleMover3d implements Mover3d {

    int xSize;
    int ySize;
    int zSize;
    private ExampleMap3d exampleMap3d;


    public ExampleMover3d(ExampleMap3d exampleMap3d) {
        this.exampleMap3d = exampleMap3d;
        xSize = 1;
        ySize = 1;
        zSize = 1;
    }

    public ExampleMover3d(ExampleMap3d exampleMap3d, int xSize, int ySize, int zSize) {
        this.exampleMap3d = exampleMap3d;
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int sz, int tx, int ty, int tz) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    if (exampleMap3d.isBlocked(tx + x, ty + y, tz + z)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean canOccupy(int tx, int ty, int tz) {
        return !exampleMap3d.isBlocked(tx, ty, tz);
    }


    @Override
    public float getCost(int sx, int sy, int sz, int tx, int ty, int tz) {
        return 1;
    }
}