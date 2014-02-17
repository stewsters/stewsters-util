package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

import java.util.LinkedList;


public class AndPredicate2d implements CellPredicate2d {

    private LinkedList<CellPredicate2d> predicates;


    public AndPredicate2d(LinkedList<CellPredicate2d> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        for (CellPredicate2d cellPredicate : predicates) {
            if (!cellPredicate.belongs(generatedMap2d, x, y)) {
                return false;
            }
        }
        return true;
    }
}
