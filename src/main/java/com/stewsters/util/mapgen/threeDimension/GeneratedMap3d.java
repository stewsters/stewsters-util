package com.stewsters.util.mapgen.threeDimension;


import com.stewsters.util.mapgen.CellType;

public interface GeneratedMap3d {

    public int getWidthInTiles();

    public int getHeightInTiles();

    public int getDepthInTiles();

    public CellType getCellTypeAt(int x, int y, int z);

    public void setCellTypeAt(int x, int y, int z, CellType cellType);


}
