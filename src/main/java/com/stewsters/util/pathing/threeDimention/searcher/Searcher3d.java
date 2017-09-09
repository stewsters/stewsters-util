package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

import java.util.List;
import java.util.Optional;

public interface Searcher3d {
    Optional<List<Point3i>> search(Mover3d mover, int x, int y, int z, Objective3d objective);
}
