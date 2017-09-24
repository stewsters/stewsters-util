package com.stewsters.util.mapgen.threeDimension;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.pathing.threeDimention.shared.BoundingBox3d;

public interface GeneratedMap3d extends BoundingBox3d{

    CellType getCellTypeAt(int x, int y, int z);

    void setCellTypeAt(int x, int y, int z, CellType cellType);

}
