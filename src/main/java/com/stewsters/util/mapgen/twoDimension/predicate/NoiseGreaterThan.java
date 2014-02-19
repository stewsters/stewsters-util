package com.stewsters.util.mapgen.twoDimension.predicate;

import com.stewsters.util.mapgen.terrain.NoiseFunction;
import com.stewsters.util.mapgen.twoDimension.GeneratedMap2d;


public class NoiseGreaterThan implements CellPredicate2d {

    private NoiseFunction noiseFunction;
    private double threshold;

    public NoiseGreaterThan(NoiseFunction noiseFunction, double threshold) {
        this.noiseFunction = noiseFunction;
        this.threshold = threshold;
    }


    @Override
    public boolean belongs(GeneratedMap2d generatedMap2d, int x, int y) {
        return noiseFunction.gen(x, y) > threshold;
    }
}
