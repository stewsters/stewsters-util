package com.stewsters.util.mapgen.twoDimension.predicate;


import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

public class CellNearEdge2d implements CellPredicate2d {


    public CellNearEdge2d() {
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return (x == 0 || y == 0 || x >= generatedMap2d.getXSize() - 1 || y >= generatedMap2d.getYSize() - 1);
    }


}
