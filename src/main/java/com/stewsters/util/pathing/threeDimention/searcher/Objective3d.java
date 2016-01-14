package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;

public interface Objective3d {

    boolean satisfiedBy(PathNode3d current);
}
