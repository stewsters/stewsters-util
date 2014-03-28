package com.stewsters.util.mapgen.threeDimension.predicate;


import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;

public class CellNearEdge3d implements CellPredicate3d {


    public CellNearEdge3d() {
    }

    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        return (x == 0 || y == 0 || z == 0
                || x >= generatedMap3d.getWidthInTiles() - 1
                || y >= generatedMap3d.getHeightInTiles() - 1
                || z >= generatedMap3d.getDepthInTiles() - 1
        );
    }


}
