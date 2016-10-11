package com.stewsters.test.examples.chunk;


import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d;

/**
 * Describes the ability to move for the entity
 */
public interface Mover2dV2 {

    boolean canTraverse(int sx, int sy, int tx, int ty);
    boolean canTraverse(Chunk chunk, int sx, int sy, int tx, int ty);

    boolean canOccupy(int tx, int ty);
    boolean canOccupy(Chunk chunk, int tx, int ty);

    float getCost(int sx, int sy, int tx, int ty);

    AStarHeuristic2d getHeuristic();

    boolean getDiagonal();
}
