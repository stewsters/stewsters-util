package com.stewsters.test.examples.chunk;


import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d;

/**
 * Describes the ability to move for the entity
 */
public interface ChunkedMover {

    boolean canTraverse(int sx, int sy, int tx, int ty);

    boolean canTraverse(ExampleChunk chunk, int sx, int sy, int tx, int ty);

    boolean canOccupy(int tx, int ty);

    boolean canOccupy(ExampleChunk chunk, int tx, int ty);

    float getCost(int sx, int sy, int tx, int ty);

    AStarHeuristic2d getHeuristic();

    boolean getDiagonal();
}
