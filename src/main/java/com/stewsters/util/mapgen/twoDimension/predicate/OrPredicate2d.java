package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

import java.util.LinkedList;


public class OrPredicate2d implements CellPredicate2d {

    private LinkedList<CellPredicate2d> predicates;


    public OrPredicate2d(LinkedList<CellPredicate2d> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        for (CellPredicate2d cellPredicate : predicates) {
            if (cellPredicate.belongs(generatedMap2d, x, y)) {
                return true;
            }
        }
        return false;
    }
}
