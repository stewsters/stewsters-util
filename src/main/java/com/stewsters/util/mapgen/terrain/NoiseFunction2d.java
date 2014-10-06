package com.stewsters.util.mapgen.terrain;

import com.stewsters.util.noise.OpenSimplexNoise;

public class NoiseFunction2d {

    private OpenSimplexNoise openSimplexNoise;
    private double xOffset, yOffset;
    private double xScale, yScale;

    public NoiseFunction2d(double xOffset, double yOffset, double xScale, double yScale) {
        openSimplexNoise = new OpenSimplexNoise();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.xScale = xScale;
        this.yScale = yScale;
    }


    /**
     * Returns a value between 0 and 1
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @return The generated value
     */
    public double gen(double x, double y) {
        return openSimplexNoise.eval((x / xScale) + xOffset, (y / yScale) + yOffset) + 0.5;
    }

}
