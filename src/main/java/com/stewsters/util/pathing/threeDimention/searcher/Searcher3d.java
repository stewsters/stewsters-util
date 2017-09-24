package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;

import java.util.List;
import java.util.Optional;

public interface Searcher3d {

    Optional<List<Point3i>> search(
            Objective3d objective,
            CanTraverse3d canTraverse3d,
            MovementCost3d movementCost3d,
            boolean allowDiagMovement,
            int sx, int sy, int sz);
}
