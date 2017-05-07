package com.stewsters.test.examples;

import com.stewsters.util.pathing.threeDimention.heuristic.AStarHeuristic3d;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public class ExampleMover3d implements Mover3d {

    private int xSize;
    private int ySize;
    private int zSize;
    private ExampleMap3d exampleMap3d;
    private AStarHeuristic3d heuristic3d;
    private boolean diagonal;

    public ExampleMover3d(ExampleMap3d exampleMap3d, AStarHeuristic3d heuristic, boolean diagonal) {
        this(exampleMap3d, heuristic, diagonal, 1, 1, 1);
    }

    public ExampleMover3d(ExampleMap3d exampleMap3d, AStarHeuristic3d heuristic, boolean diagonal, int xSize, int ySize, int zSize) {
        this.exampleMap3d = exampleMap3d;
        this.heuristic3d = heuristic;
        this.diagonal = diagonal;

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

    @Override
    public AStarHeuristic3d getHeuristic() {
        return heuristic3d;
    }

    @Override
    public boolean getDiagonal() {
        return diagonal;
    }
}