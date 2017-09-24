package com.stewsters.util.math;


import com.stewsters.util.pathing.twoDimention.shared.BoundingBox2d;
import com.stewsters.util.pathing.twoDimention.shared.CanOccupy2d;

public class Bresenham2d {

    private BoundingBox2d map2d;

    public Bresenham2d(BoundingBox2d map2d) {
        this.map2d = map2d;
    }

    public boolean los(CanOccupy2d canOccupy2d, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            if (!canOccupy2d.canOccupy(x1, y1))
                return false;

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
        return true;
    }
}
