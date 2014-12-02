package com.stewsters.test.spatial;

import com.stewsters.util.spatial.IntervalKDTree3d;

public class Spacecraft3d {
    public int x;
    public int y;
    public int z;

    public int r;

    public Spacecraft3d() {
        x = y = z = 0;
    }

    public Spacecraft3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String toString() {
        return "x:" + x + " y:" + y + " z:" + z;
    }

    public void addToTree(IntervalKDTree3d<Spacecraft3d> spacecrafts) {
        spacecrafts.put(x - r, y - r, z - r,
            x + r, y + r, z + r,
            this
        );
    }


}
