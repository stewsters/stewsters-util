package com.stewsters.test.examples;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public class ExampleGeneretedMap3d extends ExampleMap3d implements GeneratedMap3d {

    public ExampleGeneretedMap3d(int xSize, int ySize, int zSize, ExampleCellType cellType) {
        super(xSize, ySize, zSize, cellType);
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
