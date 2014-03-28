package com.stewsters.util.mapgen.threeDimension.predicate;

import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;


public class NotPredicate3d implements CellPredicate3d {

    private CellPredicate3d predicate;


    public NotPredicate3d(CellPredicate3d predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        return !predicate.belongs(generatedMap3d, x, y, z);
    }
}
