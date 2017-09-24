package com.stewsters.util.pathing.twoDimention.searcher;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;

import java.util.List;
import java.util.Optional;

public interface Searcher2d {
    Optional<List<Point2i>> search(Objective2d objective,
                                   CanTraverse2d canTraverse2d,
                                   MovementCost2d movementCost2d,
                                   boolean allowDiagMovement,
                                   int sx, int sy);
}
