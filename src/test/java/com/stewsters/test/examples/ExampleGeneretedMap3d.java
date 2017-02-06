package com.stewsters.test.examples;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public class ExampleGeneretedMap3d extends ExampleMap3d implements GeneratedMap3d {

    public ExampleGeneretedMap3d(int width, int height, int depth, ExampleCellType cellType) {
        super(width, height, depth, cellType);
    }

    @Override
    public CellType getCellTypeAt(int x, int y, int z) {
        return ground[x][y][z];
    }

    @Override
    public void setCellTypeAt(int x, int y, int z, CellType cellType) {
        ground[x][y][z] = (ExampleCellType) cellType;
    }
}
