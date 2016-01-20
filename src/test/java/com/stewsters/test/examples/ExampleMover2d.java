package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;

public class ExampleMover2d implements Mover2d {

    private ExampleMap2d exampleMap2d;
    int xSize;
    int ySize;

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
    public boolean canTraverse(PathNode2d pathNode) {
        return canTraverse(pathNode.x, pathNode.y);
    }

    @Override
    public boolean canTraverse(int xPos, int yPos) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (exampleMap2d.isBlocked(xPos + x, yPos + y)) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        return 1;
    }
}