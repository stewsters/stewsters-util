package com.stewsters.util.mapgen.twoDimension;


import com.stewsters.util.mapgen.CellType;

public interface GeneratedMap2d {

    public int getXSize();

    public int getYSize();

    public CellType getCellTypeAt(int x, int y);

    public void setCellTypeAt(int x, int y, CellType cellType);
}
