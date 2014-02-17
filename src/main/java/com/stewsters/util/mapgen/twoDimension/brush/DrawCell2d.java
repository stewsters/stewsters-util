package com.stewsters.util.mapgen.twoDimension.brush;


import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

public class DrawCell2d implements Brush2d {

    CellType cellType;

    public DrawCell2d(CellType tileType) {
        this.cellType = tileType;
    }

    @Override
    public void draw(GeneratedMap2d generatedMap2d, int x, int y) {
        generatedMap2d.setCellTypeAt(x, y, cellType);
    }
}
