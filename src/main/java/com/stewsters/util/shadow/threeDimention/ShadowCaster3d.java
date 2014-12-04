package com.stewsters.util.shadow.threeDimention;

import com.stewsters.util.math.Facing2d;

public class ShadowCaster3d {

    //There are errors here.  Look here for improvements.
    //  http://www.roguebasin.com/index.php?title=FOV_using_recursive_shadowcasting_-_improved

    private int startx, starty, startz;
    private float force, decay, radius;
    private LitMap3d litMap3d;


    /**
     * This is a special case of SquidLib's shadow casting algorithm by Eben Howard
     *
     * @param litMap3d The map we are working on
     */
    public ShadowCaster3d(LitMap3d litMap3d) {
        this.litMap3d = litMap3d;
    }


    /**
     * <p>
     * Calculates the Field Of View for the provided map from the given x, y
     * coordinates. Returns a lightmap for a result where the values represent a
     * percentage of fully lit.
     * </p>
     * <p>
     * A value equal to or below 0 means that cell is not in the
     * field of view, whereas a value equal to or above 1 means that cell is
     * in the field of view.
     * </p>
     *
     * @param startx the z component of the starting location
     * @param starty the y component of the starting location
     * @param startz the z component of the starting location
     * @param force  the maximum distance to draw the FOV
     * @param decay  the speed of the decay
     */
    public void recalculateFOV(int startx, int starty, int startz, float force, float decay) {

        this.startx = startx;
        this.starty = starty;
        this.startz = startz;

        this.force = force;
        this.decay = decay;

        this.radius = (force / decay);

        litMap3d.setLight(startx, starty, startz, force);


        for (Facing2d d : Facing2d.values()) {
            castLight(1, 1.0f, 0.0f, 0, d.x, d.y, 0);
            castLight(1, 1.0f, 0.0f, d.x, 0, 0, d.y);
        }
    }

    private void castLight(int row, float start, float end, int xx, int xy, int yx, int yy) {
        float newStart = 0.0f;
        if (start < end) {
            return;
        }
        boolean blocked = false;
        for (int distance = row; distance <= radius && !blocked; distance++) {
            int deltaY = -distance;
            for (int deltaX = -distance; deltaX <= 0; deltaX++) {
                int currentX = startx + deltaX * xx + deltaY * xy;
                int currentY = starty + deltaX * yx + deltaY * yy;
                int currentZ = startz;

                float leftSlope = (deltaX - 0.5f) / (deltaY + 0.5f);
                float rightSlope = (deltaX + 0.5f) / (deltaY - 0.5f);

                if (!(currentX >= 0 && currentY >= 0 && currentX < litMap3d.getWidthInTiles() && currentY < litMap3d.getHeightInTiles()) || start < rightSlope) {
                    continue;
                } else if (end > leftSlope) {
                    break;
                }

                //check if it's within the lightable area and light if needed
                if (radius(deltaX, deltaY, currentZ) <= force) {
                    float bright = (1 - (decay * radius(deltaX, deltaY, currentZ) / force));
                    litMap3d.setLight(currentX, currentY, currentZ, bright);
                }

                if (blocked) { //previous cell was a blocking one
                    if (litMap3d.getResistance(currentX, currentY, currentZ) >= 1) {//hit a wall
                        newStart = rightSlope;
                        continue;
                    } else {
                        blocked = false;
                        start = newStart;
                    }
                } else {
                    if (litMap3d.getResistance(currentX, currentY, currentZ) >= 1 && distance < force) {//hit a wall within sight line
                        blocked = true;
                        castLight(distance + 1, start, leftSlope, xx, xy, yx, yy);
                        newStart = rightSlope;
                    }
                }
            }
        }
    }

    /**
     * @param dx The distance in the x direction
     * @param dy The distance in the y direction
     * @param dz The distance in the z direction
     * @return The length of the vector
     */
    private float radius(float dx, float dy, float dz) {
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
}
