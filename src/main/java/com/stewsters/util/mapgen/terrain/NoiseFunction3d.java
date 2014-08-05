package com.stewsters.util.mapgen.terrain;

import com.stewsters.util.noise.Simplex3d;

/**
 * This basically encapsulates the scaling of noise into an object
 */
public class NoiseFunction3d {
    private float xOffset, yOffset, zOffset;
    private float xScale, yScale, zScale;

    public NoiseFunction3d(float xOffset, float yOffset, float zOffset, float xScale, float yScale, float zScale) {
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
    public float gen(float x, float y, float z) {
        return Simplex3d.noise((x / xScale) + xOffset, (y / yScale) + yOffset, (z / zScale) + zOffset) + 0.5f;
    }

}
