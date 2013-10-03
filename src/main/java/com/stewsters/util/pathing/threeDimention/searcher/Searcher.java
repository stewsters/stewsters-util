package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.pathing.threeDimention.shared.FullPath;
import com.stewsters.util.pathing.threeDimention.shared.Mover;

public interface Searcher {
    public FullPath search(Mover mover, int x, int y, int z, Objective objective);
}
