package com.stewsters.util.mapgen.twoDimension;


import com.stewsters.util.mapgen.CellType;

public interface GeneratedMap2d {

    int getXSize();

    int getYSize();

    CellType getCellTypeAt(int x, int y);

    void setCellTypeAt(int x, int y, CellType cellType);

    boolean isOutside(int x, int y);
}
