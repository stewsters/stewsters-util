package com.stewsters.util.mapgen.threeDimension;


import com.stewsters.util.mapgen.CellType;

public interface GeneratedMap3d {

    public int getXSize();

    public int getYSize();

    public int getZSize();

    public CellType getCellTypeAt(int x, int y, int z);

    public void setCellTypeAt(int x, int y, int z, CellType cellType);


}
