package com.stewsters.util.math.geom;


import com.stewsters.util.math.Point3i;

public class RectPrism {

    public int x1;
    public int x2;
    public int y1;
    public int y2;
    public int z1;
    public int z2;

    public RectPrism(int x1, int y1, int z1, int x2, int y2, int z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public Point3i center() {
        int center_x = (x1 + x2) / 2;
        int center_y = (y1 + y2) / 2;
        int center_z = (z1 + z2) / 2;
        return new Point3i(center_x, center_y, center_z);
    }

    public boolean intersect(RectPrism other) {
        return (x1 <= other.x2 &&
                x2 >= other.x1 &&
                y1 <= other.y2 &&
                y2 >= other.y1 &&
                z1 <= other.z2 &&
                z2 >= other.z1
        );
    }

    public String toString() {
        return x1 + " " + y1 + " " + z1 + " " + x2 + " " + y2 + " " + z2;
    }

    public boolean contains(int x, int y, int z) {
        return (x >= x1 && x <= x2 &&
                y >= y1 && y <= y2 &&
                z >= z1 && z <= z2
        );
    }


}
