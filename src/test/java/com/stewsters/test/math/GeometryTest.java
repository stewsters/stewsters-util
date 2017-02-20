package com.stewsters.test.math;

import com.stewsters.util.math.geom.Rect;
import org.junit.Test;

public class GeometryTest {

    @Test
    public void Rectangle() {

        Rect r1 = new Rect(10, 20, 20, 25);
        Rect r2 = new Rect(1, 1, 11, 21);
        Rect r3 = new Rect(1, 1, 5, 5);
        Rect r4 = new Rect(0, 0, 100, 100);

        assert r1.contains(10, 20);
        assert r1.contains(20, 25);

        assert !r1.contains(10, 19);
        assert !r1.contains(9, 20);
        assert !r1.contains(21, 25);
        assert !r1.contains(20, 26);

        assert r1.center().x == 15;
        assert r1.center().y == 22;

        assert r1.intersect(r2);

        assert r1.intersect(r2);
        assert r2.intersect(r1);
        assert !r1.intersect(r3);
        assert !r3.intersect(r1);
        assert r2.intersect(r3);
        assert r3.intersect(r2);

        assert r4.intersect(r1);
        assert r1.intersect(r4);

        assert r1.toString().equals("10 20 20 25");

    }
}
