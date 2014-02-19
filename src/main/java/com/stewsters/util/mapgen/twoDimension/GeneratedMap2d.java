package com.stewsters.util.mapgen.twoDimension;


import com.stewsters.util.mapgen.CellType;

public interface GeneratedMap2d {

    public int getWidthInTiles();

    public int getHeightInTiles();

    public CellType getCellTypeAt(int x, int y);

    public void setCellTypeAt(int x, int y, CellType cellType);
}
