package com.stewsters.util.math.geom;

import com.stewsters.util.math.Point2i;

/**
 * int rectangle
 */
public class Rect {
    public int x1;
    public int x2;
    public int y1;
    public int y2;


    public Rect(int x, int y, int w, int h) {
        this.x1 = x;
        this.y1 = y;
        this.x2 = x + w - 1;
        this.y2 = y + h - 1;
    }

    public Point2i center() {
        int center_x = (x1 + x2) / 2;
        int center_y = (y1 + y2) / 2;
        return new Point2i(center_x, center_y);
    }

    public boolean intersect(Rect other) {
        return (x1 <= other.x2 &&
                x2 >= other.x1 &&
                y1 <= other.y2 &&
                y2 >= other.y1);
    }

    public String toString() {
        return "$x1 $y1 $x2 $y2";
    }

    boolean contains(int x, int y) {
        return (x >= x1 && x <= x2 &&
                y >= y1 && y <= y2
        );
    }
}
