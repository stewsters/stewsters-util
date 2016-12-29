package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

public class ExampleMover2d implements Mover2d {

    int xSize;
    int ySize;
    private ExampleMap2d exampleMap2d;

    public ExampleMover2d(ExampleMap2d exampleMap2d) {
        this.exampleMap2d = exampleMap2d;
        xSize = 1;
        ySize = 1;
    }

    public ExampleMover2d(ExampleMap2d exampleMap2d, int xSize, int ySize) {
        this.exampleMap2d = exampleMap2d;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int tx, int ty) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (exampleMap2d.isBlocked(tx + x, ty + y)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean canOccupy(int tx, int ty) {
        return !exampleMap2d.isBlocked(tx, ty);
    }

    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        return ((sx == tx) || (sy == ty)) ? 1f : 1f;
    }
}