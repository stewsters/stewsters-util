package com.stewsters.util.mapgen.threeDimension.predicate;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public class CellEquals3d implements CellPredicate3d {

    CellType tileType;

    public CellEquals3d(CellType tileType) {
        this.tileType = tileType;
    }

    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        return generatedMap3d.getCellTypeAt(x, y, z) == tileType;
    }
}
