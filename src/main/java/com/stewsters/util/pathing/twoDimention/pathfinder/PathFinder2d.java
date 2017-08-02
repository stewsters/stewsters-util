package com.stewsters.util.pathing.twoDimention.pathfinder;


import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

import java.util.List;
import java.util.Optional;

/**
 * A description of an implementation that can find a path from one
 * location on a tile map to another based on information provided
 * by that tile map.
 *
 * @author Kevin Glass
 * @see com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d
 */
public interface PathFinder2d {

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
     * @param tx    The x coordinate of the target location
     * @param ty    Teh y coordinate of the target location
     * @return The path found from start to end, or null if no path can be found.
     */
    Optional<List<Point2i>> findPath(Mover2d mover, int sx, int sy, int tx, int ty);
}