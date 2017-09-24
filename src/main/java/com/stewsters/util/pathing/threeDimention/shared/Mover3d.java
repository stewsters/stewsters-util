package com.stewsters.util.pathing.threeDimention.shared;


import com.stewsters.util.pathing.threeDimention.heuristic.AStarHeuristic3d;

/**
 * <p>
 * A tagging interface for an object representing the entity in the game that
 * is going to moving along the path. This allows us to pass around entity/state
 * information to determine whether a particular tile is blocked, or how much
 * cost to apply on a particular tile.
 * </p>
 * <p>
 * For instance, a Mover might represent a tank or plane on a game map. Passing round
 * this entity allows us to determine whether rough ground on a map should effect
 * the unit's cost for moving through the tile.
 * </p>
 *
 * @author Kevin Glass
 */
@Deprecated
public interface Mover3d {

    boolean canTraverse(int sx, int sy, int sz, int tx, int ty, int tz);

    boolean canOccupy(int tx, int ty, int tz);

    float getCost(int sx, int sy, int sz, int tx, int ty, int tz);

    AStarHeuristic3d getHeuristic();

    boolean getDiagonal();
}
