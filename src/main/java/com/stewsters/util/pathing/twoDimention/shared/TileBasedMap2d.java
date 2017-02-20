package com.stewsters.util.pathing.twoDimention.shared;

/**
 * The description for the data we're pathfinding over. This provides the contract
 * between the data being searched (i.e. the in game map) and the path finding
 * generic tools
 *
 * @author Kevin Glass
 */
public interface TileBasedMap2d {

    int getXSize();

    int getYSize();

    boolean isOutside(int x, int y);
}