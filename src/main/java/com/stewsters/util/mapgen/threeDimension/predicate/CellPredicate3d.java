package com.stewsters.util.mapgen.threeDimension.predicate;

import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public interface CellPredicate3d {

    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z);

}
