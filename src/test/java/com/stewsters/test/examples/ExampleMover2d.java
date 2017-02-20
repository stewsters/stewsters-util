package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

public class ExampleMover2d implements Mover2d {

    private int xSize;
    private int ySize;
    private ExampleMap2d exampleMap2d;
    private AStarHeuristic2d heuristic2d;
    private boolean diagonal;

    public ExampleMover2d(ExampleMap2d exampleMap2d, AStarHeuristic2d heuristic, boolean diagonal) {
        this(exampleMap2d, heuristic, diagonal, 1, 1);
    }

    public ExampleMover2d(ExampleMap2d exampleMap2d, AStarHeuristic2d heuristic, boolean diagonal, int xSize, int ySize) {
        this.exampleMap2d = exampleMap2d;
        this.heuristic2d = heuristic;
        this.diagonal = diagonal;
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

    @Override
    public AStarHeuristic2d getHeuristic() {
        return heuristic2d;
    }

    @Override
    public boolean getDiagonal() {
        return diagonal;
    }
}