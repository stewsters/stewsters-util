package com.stewsters.util.pathing.twoDimention.searcher;

import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;

public interface Objective2d {

    public boolean satisfiedBy(PathNode2d current);
}
