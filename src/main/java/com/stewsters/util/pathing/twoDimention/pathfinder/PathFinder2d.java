package com.stewsters.util.pathing.twoDimention.pathfinder;


import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.CanOccupy2d;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;

import java.util.List;
import java.util.Optional;

public interface PathFinder2d {

    Optional<List<Point2i>> findPath(
            CanTraverse2d canTraverse2d,
            CanOccupy2d canOccupy2d,
            MovementCost2d movementCost2d,
            AStarHeuristic2d heuristic,
            boolean allowDiagMovement,
            int sx, int sy, int tx, int ty);
}