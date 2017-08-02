package com.stewsters.util.pathing.twoDimention.searcher;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

import java.util.List;
import java.util.Optional;

public interface Searcher2d {
    Optional<List<Point2i>> search(Mover2d mover, int x, int y, Objective2d objective);
}
