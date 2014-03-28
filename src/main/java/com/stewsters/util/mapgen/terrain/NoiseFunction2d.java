package com.stewsters.util.mapgen.terrain;

import com.stewsters.util.noise.Simplex2d;

public class NoiseFunction2d {
    private double xOffset, yOffset;
    private double xScale, yScale;

    public NoiseFunction2d(double xOffset, double yOffset, double xScale, double yScale) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xScale = xScale;
        this.yScale = yScale;
    }


    /**
     * Returns a value between 0 and 1
     *
     * @param x
     * @param y
     * @return
     */
    public double gen(double x, double y) {
        return Simplex2d.noise((x / xScale) + xOffset, (y / yScale) + yOffset) + 0.5;
    }

}
