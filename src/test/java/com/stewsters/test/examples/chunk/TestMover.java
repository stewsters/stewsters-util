package com.stewsters.test.examples.chunk;

import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.ManhattanHeuristic2d;

public class TestMover implements ChunkedMover {

    private AStarHeuristic2d heuristic;
    private OverworldExample overworldExample;

    public TestMover(OverworldExample overworldExample) {
        this.overworldExample = overworldExample;
        heuristic = new ManhattanHeuristic2d();
    }

    @Override
    public boolean canTraverse(int sx, int sy, int tx, int ty) {

        return overworldExample.isOutsideMap(tx, ty) && !overworldExample.isBlocking(tx, ty);
    }

    @Override
    public boolean canTraverse(ExampleChunk chunk, int sx, int sy, int tx, int ty) {
        return !chunk.ground[tx][ty].isBlocking();
    }

    @Override
    public boolean canOccupy(int tx, int ty) {
        return !overworldExample.isBlocking(tx, ty);
    }

    @Override
    public boolean canOccupy(ExampleChunk chunk, int tx, int ty) {
        return !chunk.ground[tx][ty].isBlocking();
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
        return false;
    }
}
