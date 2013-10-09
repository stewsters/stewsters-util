package com.stewsters.util.pathing.threeDimention.pathfinder;

import com.stewsters.util.pathing.threeDimention.shared.Mover3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

/**
 * The description of a class providing a cost for a given tile based
 * on a target location and entity being moved. This heuristic controls
 * what priority is placed on different tiles during the search for a path
 *
 * @author Kevin Glass
 */
public interface AStarHeuristic3d {

    /**
     * Get the additional heuristic cost of the given tile. This controls the
     * order in which tiles are searched while attempting to find a path to the
     * target location. The lower the cost the more likely the tile will
     * be searched.
     *
     * @param map   The map on which the path is being found
     * @param mover The entity that is moving along the path
     * @param x     The x coordinate of the tile being evaluated
     * @param y     The y coordinate of the tile being evaluated
     * @param tx    The x coordinate of the target location
     * @param ty    Teh y coordinate of the target location
     * @return The cost associated with the given tile
     */
    public float getCost(TileBasedMap3d map, Mover3d mover, int x, int y, int z, int tx, int ty, int tz);
}