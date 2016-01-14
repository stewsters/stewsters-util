package com.stewsters.util.pathing.threeDimention.searcher;

import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.threeDimention.shared.Mover3d;

public interface Searcher3d {
    FullPath3d search(Mover3d mover, int x, int y, int z, Objective3d objective);
}
