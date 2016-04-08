package com.stewsters.test.examples;


import com.stewsters.util.math.Evaluator3d;

public class ClearShotEvaluator implements Evaluator3d {

    private ExampleMap3d map;

    public ClearShotEvaluator(ExampleMap3d map) {
        this.map = map;
    }

    @Override
    public boolean isGood(int sx, int sy, int sz, int tx, int ty, int tz) {
        return !map.isBlocked(tx, ty, tz);
    }
}
