package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.terrain.NoiseFunction2d;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;


public class NoiseGreaterThan2d implements CellPredicate2d {

    private NoiseFunction2d noiseFunction2d;
    private double threshold;

    public NoiseGreaterThan2d(NoiseFunction2d noiseFunction2d, double threshold) {
        this.noiseFunction2d = noiseFunction2d;
        this.threshold = threshold;
    }


    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return noiseFunction2d.gen(x, y) > threshold;
    }
}
