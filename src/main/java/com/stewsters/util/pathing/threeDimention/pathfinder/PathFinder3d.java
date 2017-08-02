package com.stewsters.util.pathing.threeDimention.pathfinder;


import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

import java.util.List;
import java.util.Optional;

/**
 * A description of an implementation that can find a path from one
 * location on a tile map to another based on information provided
 * by that tile map.
 *
 * @author Kevin Glass
 * @see com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d
 */
public interface PathFinder3d {

    /**
     * Find a path from the starting location provided (sx,sy) to the target
     * location (tx,ty) avoiding blockages and attempting to honour costs
     * provided by the tile map.
     *
     * @param mover The entity that will be moving along the path. This provides
     *              a place to pass context information about the game entity doing the moving, e.g.
     *              can it fly? can it swim etc.
     * @param sx    The x coordinate of the start location
     * @param sy    The y coordinate of the start location
     * @param sz    The z coordinate of the start location
     * @param tx    The x coordinate of the target location
     * @param ty    The y coordinate of the target location
     * @param tz    The z coordinate of the target location
     * @return The path found from start to end, or null if no path can be found.
     */
    Optional<List<Point3i>> findPath(Mover3d mover, int sx, int sy, int sz, int tx, int ty, int tz);
}