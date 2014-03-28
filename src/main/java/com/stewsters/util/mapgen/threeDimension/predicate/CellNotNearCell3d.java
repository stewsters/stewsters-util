package com.stewsters.util.mapgen.threeDimension.predicate;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public class CellNotNearCell3d implements CellPredicate3d {

    CellType cellType;

    public CellNotNearCell3d(CellType cellType) {
        this.cellType = cellType;
    }

    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {
                for (int iz = -1; iz <= 1; iz++) {
                    if (ix == 0 && iy == 0 && iz == 0)
                        continue;
                    if (generatedMap3d.getCellTypeAt(x + ix, y + iy, z + iz) == cellType)
                        return false;
                }
            }
        }
        return true;
    }


}
