package com.stewsters.util.mapgen.twoDimension;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.pathing.twoDimention.shared.BoundingBox2d;

public interface GeneratedMap2d extends BoundingBox2d {

    CellType getCellTypeAt(int x, int y);

    void setCellTypeAt(int x, int y, CellType cellType);
}
