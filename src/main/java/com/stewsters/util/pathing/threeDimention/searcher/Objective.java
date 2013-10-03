package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.pathing.threeDimention.shared.PathNode;

public interface Objective {

    public boolean satisfiedBy(PathNode current);
}
