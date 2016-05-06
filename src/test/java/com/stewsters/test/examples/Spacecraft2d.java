package com.stewsters.test.examples;

import com.stewsters.util.spatial.IntervalKDTree2d;

public class Spacecraft2d {
    public int x;
    public int y;

    public int r;

    public Spacecraft2d() {
        x = y = 0;
    }

    public Spacecraft2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.r = 1;
    }

    public String toString() {
        return "x:" + x + " y:" + y;
    }

    public void addToTree(IntervalKDTree2d<Spacecraft2d> spacecrafts) {
        spacecrafts.put(x - r, y - r,
                x + r, y + r,
                this
        );
    }


}
