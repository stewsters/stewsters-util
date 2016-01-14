package com.stewsters.util.pathing.twoDimention.shared;


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
public interface Mover2d {

    boolean canTraverse(PathNode2d pathNode);

    float getCost(Mover2d mover, int sx, int sy, int tx, int ty);

}
