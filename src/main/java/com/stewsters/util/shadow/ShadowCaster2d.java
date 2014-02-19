package com.stewsters.util.shadow;

import com.stewsters.util.math.Facing2d;

public class ShadowCaster2d {

    //There are errors here.  Look here for improvements.
    //  http://www.roguebasin.com/index.php?title=FOV_using_recursive_shadowcasting_-_improved

    private int startx, starty;
    private float force, decay, radius;
    private LitMap2d litMap2d;


    /**
     * This is a special case of SquidLib's shadow casting algorithm by Eben Howard
     *
     * @param litMap2d
     */
    public ShadowCaster2d(LitMap2d litMap2d) {
        this.litMap2d = litMap2d;
    }


    /**
     * Calculates the Field Of View for the provided map from the given x, y
     * coordinates. Returns a lightmap for a result where the values represent a
     * percentage of fully lit.
     * <p/>
     * A value equal to or below 0 means that cell is not in the
     * field of view, whereas a value equal to or above 1 means that cell is
     * in the field of view.
     *
     * @param startx the horizontal component of the starting location
     * @param starty the vertical component of the starting location
     * @param force  the maximum distance to draw the FOV
     * @param decay  the speed of the decay
     * @return the computed light grid
     */
    public void recalculateFOV(int startx, int starty, float force, float decay) {

        this.startx = startx;
        this.starty = starty;
        this.force = force;
        this.decay = decay;

        this.radius = (force / decay);

        litMap2d.setLight(startx, starty, force);


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
                float leftSlope = (deltaX - 0.5f) / (deltaY + 0.5f);
                float rightSlope = (deltaX + 0.5f) / (deltaY - 0.5f);

                if (!(currentX >= 0 && currentY >= 0 && currentX < litMap2d.getWidthInTiles() && currentY < litMap2d.getHeightInTiles()) || start < rightSlope) {
                    continue;
                } else if (end > leftSlope) {
                    break;
                }

                //check if it's within the lightable area and light if needed
                if (radius(deltaX, deltaY) <= force) {
                    float bright = (1 - (decay * radius(deltaX, deltaY) / force));
                    litMap2d.setLight(currentX, currentY, bright);
                }

                if (blocked) { //previous cell was a blocking one
                    if (litMap2d.getResistance(currentX, currentY) >= 1) {//hit a wall
                        newStart = rightSlope;
                        continue;
                    } else {
                        blocked = false;
                        start = newStart;
                    }
                } else {
                    if (litMap2d.getResistance(currentX, currentY) >= 1 && distance < force) {//hit a wall within sight line
                        blocked = true;
                        castLight(distance + 1, start, leftSlope, xx, xy, yx, yy);
                        newStart = rightSlope;
                    }
                }
            }
        }
    }

    private float radius(float dx, float dy) {
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
}