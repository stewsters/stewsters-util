package com.stewsters.util.mapgen.threeDimension.predicate;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

import java.util.LinkedList;

public class CellEqualAny3d implements CellPredicate3d {

    LinkedList<CellType> tileTypes;

    public CellEqualAny3d(LinkedList<CellType> tileTypes) {
        this.tileTypes = tileTypes;
    }

    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        return tileTypes.contains(generatedMap3d.getCellTypeAt(x, y, z));
    }
}
