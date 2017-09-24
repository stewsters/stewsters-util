package com.stewsters.util.pathing.twoDimention.pathfinder;


import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.CanOccupy;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost;

import java.util.List;
import java.util.Optional;

public interface PathFinder2d {

    Optional<List<Point2i>> findPath(
            CanTraverse canTraverse,
            CanOccupy canOccupy,
            MovementCost movementCost,
            AStarHeuristic2d heuristic,
            boolean allowDiagMovement,
            int sx, int sy, int tx, int ty);
}