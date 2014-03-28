package com.stewsters.util.mapgen.threeDimension.predicate;

import com.stewsters.util.mapgen.terrain.NoiseFunction3d;
import com.stewsters.util.mapgen.threeDimension.GeneratedMap3d;


public class NoiseGreaterThan implements CellPredicate3d {

    private NoiseFunction3d noiseFunction3d;
    private double threshold;

    public NoiseGreaterThan(NoiseFunction3d noiseFunction3d, double threshold) {
        this.noiseFunction3d = noiseFunction3d;
        this.threshold = threshold;
    }


    @Override
    public boolean belongs(GeneratedMap3d generatedMap3d, int x, int y, int z) {
        return noiseFunction3d.gen(x, y, z) > threshold;
    }
}
