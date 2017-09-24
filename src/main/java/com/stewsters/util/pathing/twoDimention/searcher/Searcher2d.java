package com.stewsters.util.pathing.twoDimention.searcher;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost;

import java.util.List;
import java.util.Optional;

public interface Searcher2d {
    Optional<List<Point2i>> search(Objective2d objective,
                                   CanTraverse canTraverse,
                                   MovementCost movementCost,
                                   boolean allowDiagMovement,
                                   int sx, int sy);
}
