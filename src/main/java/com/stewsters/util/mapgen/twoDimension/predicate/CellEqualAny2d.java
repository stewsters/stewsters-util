package com.stewsters.util.mapgen.twoDimension.predicate;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

import java.util.List;

public class CellEqualAny2d implements CellPredicate2d {

    List<CellType> tileTypes;

    public CellEqualAny2d(List<CellType> tileTypes) {
        this.tileTypes = tileTypes;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return tileTypes.contains(generatedMap2d.getCellTypeAt(x, y));
    }
}
