package com.stewsters.util.pathing.twoDimention.searcher;

import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;

public interface Searcher2d {
    public FullPath2d search(Mover2d mover, int x, int y, Objective2d objective);
}
