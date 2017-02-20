package com.stewsters.util.mapgen.threeDimension;


import com.stewsters.util.mapgen.CellType;

public interface GeneratedMap3d {

    int getXSize();

    int getYSize();

    int getZSize();

    CellType getCellTypeAt(int x, int y, int z);

    void setCellTypeAt(int x, int y, int z, CellType cellType);

    boolean isOutside(int x, int y, int z);
}
