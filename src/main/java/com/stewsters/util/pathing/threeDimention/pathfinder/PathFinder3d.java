package com.stewsters.util.pathing.threeDimention.pathfinder;


import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.AStarHeuristic3d;
import com.stewsters.util.pathing.threeDimention.shared.CanOccupy3d;
import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;

import java.util.List;
import java.util.Optional;

public interface PathFinder3d {

    Optional<List<Point3i>> findPath(CanTraverse3d canTraverse3d,
                                     CanOccupy3d canOccupy3d,
                                     MovementCost3d movementCost3d,
                                     AStarHeuristic3d heuristic,
                                     boolean allowDiagMovement,
                                     int sx, int sy, int sz, int tx, int ty, int tz);
}