package com.stewsters.util.mapgen.threeDimension.predicate;

import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;


public class AndPredicate3d implements CellPredicate3d {

    private CellPredicate3d[] predicates;

    public AndPredicate3d(CellPredicate3d... predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        for (CellPredicate3d cellPredicate : predicates) {
            if (!cellPredicate.belongs(generatedMap3d, x, y, z)) {
                return false;
            }
        }
        return true;
    }
}
