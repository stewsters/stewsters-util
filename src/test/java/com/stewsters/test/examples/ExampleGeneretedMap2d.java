package com.stewsters.test.examples;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;

public class ExampleGeneretedMap2d extends ExampleMap2d implements GeneratedMap2d {

    public ExampleGeneretedMap2d(int width, int height, CellType cellType) {
        super(width, height, cellType);


    }

    @Override
    public CellType getCellTypeAt(int x, int y) {
        return ground[x][y];
    }

    @Override
    public CellType setCellTypeAt(int x, int y, CellType cellType) {
        return ground[x][y] = cellType;
    }
}
