package com.stewsters.test.examples.chunk;

import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

public class ExampleChunkedMover2d implements Mover2d {

    private final boolean diagonal;
    private final AStarHeuristic2d heuristic;
    private OverworldExample overworldExample;


    public ExampleChunkedMover2d(OverworldExample overworldExample, AStarHeuristic2d heuristic, boolean diagonal) {
        this.overworldExample = overworldExample;
        this.heuristic = heuristic;
        this.diagonal = diagonal;
    }

    @Override
    public boolean canTraverse(int sx, int sy, int tx, int ty) {

        return !overworldExample.isOutside(tx, ty) && !overworldExample.isBlocking(tx, ty);
    }

    @Override
    public boolean canOccupy(int tx, int ty) {
        return !overworldExample.isBlocking(tx, ty);
    }

    @Override
    public float getCost(int sx, int sy, int tx, int ty) {
        return 1;
    }

    @Override
    public AStarHeuristic2d getHeuristic() {
        return heuristic;
    }

    @Override
    public boolean getDiagonal() {
        return diagonal;
    }
}
