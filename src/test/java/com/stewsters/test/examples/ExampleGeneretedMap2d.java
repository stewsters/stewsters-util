package com.stewsters.test.examples;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

public class ExampleGeneretedMap2d extends ExampleMap2d implements GeneratedMap2d {

    public ExampleGeneretedMap2d(int xSize, int ySize, ExampleCellType cellType) {
        super(xSize, ySize, cellType);
    }

    @Override
    public CellType getCellTypeAt(int x, int y) {
        return ground[x][y];
    }

    @Override
    public void setCellTypeAt(int x, int y, CellType cellType) {
        ground[x][y] = (ExampleCellType) cellType;
    }
}
