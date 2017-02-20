package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

public class CellNotNearCell2d implements CellPredicate2d {

    CellType cellType;

    public CellNotNearCell2d(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                if (ix == 0 && iy == 0)
                    continue;

                if (generatedMap2d.isOutside(x + ix, y + iy))
                    continue;

                if (generatedMap2d.getCellTypeAt(x + ix, y + iy) == cellType)
                    return false;
            }
        }
        return true;
    }
}
