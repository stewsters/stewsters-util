package com.stewsters.util.mapgen.terrain;

import com.stewsters.util.noise.OpenSimplexNoise;


public class NoiseFunction3d {

    private OpenSimplexNoise openSimplexNoise;
    private double xOffset, yOffset, zOffset;
    private double xScale, yScale, zScale;

    public NoiseFunction3d(double xOffset, double yOffset, double zOffset, double xScale, double yScale, double zScale) {
        openSimplexNoise = new OpenSimplexNoise();
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
    }


    /**
     * Returns a value between 0 and 1
     *
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @return The generated value scaled
     */
    public double gen(double x, double y, double z) {
        return openSimplexNoise.eval((x / xScale) + xOffset, (y / yScale) + yOffset, (z / zScale) + zOffset) + 0.5f;
    }

}
