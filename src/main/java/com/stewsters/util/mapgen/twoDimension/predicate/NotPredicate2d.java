package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;


public class NotPredicate2d implements CellPredicate2d {

    private CellPredicate2d predicate;


    public NotPredicate2d(CellPredicate2d predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return !predicate.belongs(generatedMap2d, x, y);
    }
}
