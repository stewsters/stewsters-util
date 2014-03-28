package com.stewsters.util.mapgen.threeDimension.brush;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public class DrawCell3d implements Brush3d {

    CellType cellType;

    public DrawCell3d(CellType tileType) {
        this.cellType = tileType;
    }

    @Override
    public void draw(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        generatedMap3d.setCellTypeAt(x, y, z, cellType);
    }
}
