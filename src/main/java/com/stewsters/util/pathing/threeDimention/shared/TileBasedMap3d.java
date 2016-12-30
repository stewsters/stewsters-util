package com.stewsters.util.pathing.threeDimention.shared;

/**
 * The description for the data we're pathfinding over. This provides the contract
 * between the data being searched (i.e. the in game map) and the path finding
 * generic tools
 *
 * @author Kevin Glass
 */
public interface TileBasedMap3d {
    /**
     * Get the width of the tile map. The slightly odd name is used
     * to distiguish this method from commonly used names in game maps.
     *
     * @return The number of tiles across the map
     */
    int getXSize();

    /**
     * Get the height of the tile map. The slightly odd name is used
     * to distiguish this method from commonly used names in game maps.
     *
     * @return The number of tiles down the map
     */
    int getYSize();

    /**
     * Get the depth of the tile map. The slightly odd name is used
     * to distiguish this method from commonly used names in game maps.
     *
     * @return The number of tiles down the map
     */
    int getZSize();

}
