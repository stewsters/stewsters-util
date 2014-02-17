package com.stewsters.util.mapgen.twoDimension.predicate;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

public class CellEquals2d implements CellPredicate2d {

    CellType tileType;

    public CellEquals2d(CellType tileType) {
        this.tileType = tileType;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return generatedMap2d.getCellTypeAt(x, y) == tileType;
    }
}
