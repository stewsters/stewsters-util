package com.stewsters.util.pathing.twoDimention.hpa;


import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 * Describes the ability to move for the entity
 */
public interface ChunkedMover2d extends Mover2d {

    boolean canTraverse(TileBasedMap2d chunk, int sx, int sy, int tx, int ty);

    boolean canOccupy(TileBasedMap2d chunk, int tx, int ty);

    float getCost(int sx, int sy, int tx, int ty);

}
