package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;
import com.stewsters.util.math.geom.Container2d;

public class CellContainedIn2d implements CellPredicate2d {

    Container2d container;

    public CellContainedIn2d(Container2d container) {
        this.container = container;
    }

    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return container.contains(x, y);
    }
}
