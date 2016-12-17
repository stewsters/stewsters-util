package com.stewsters.test.math;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.Point3i;
import org.junit.Test;

import java.util.List;

public class PointTest {

    @Test
    public void testVonNeuman() {

        Point2i initial = new Point2i(0, 0);
        List<Point2i> points = initial.vonNeumannNeighborhood();

        assert points.size() == 4;

        assert points.contains(new Point2i(0, 1));
        assert points.contains(new Point2i(0, -1));
        assert points.contains(new Point2i(1, 0));
        assert points.contains(new Point2i(-1, 0));

    }

    @Test
    public void rotatedVonNeumannNeighborhood() {
        Point2i initial = new Point2i(0, 0);
        List<Point2i> points = initial.rotatedVonNeumannNeighborhood();

        assert points.size() == 4;

        assert points.contains(new Point2i(1, 1));
        assert points.contains(new Point2i(-1, -1));
        assert points.contains(new Point2i(1, -1));
        assert points.contains(new Point2i(-1, 1));

    }

    @Test
    public void mooreNeighborhood() {
        Point2i initial = new Point2i(0, 0);
        List<Point2i> points = initial.mooreNeighborhood();

        assert points.size() == 8;

        assert points.contains(new Point2i(-1, 1));
        assert points.contains(new Point2i(0, 1));
        assert points.contains(new Point2i(1, 1));

        assert points.contains(new Point2i(-1, 0));
        assert points.contains(new Point2i(1, 0));

        assert points.contains(new Point2i(-1, -1));
        assert points.contains(new Point2i(0, -1));
        assert points.contains(new Point2i(1, -1));

    }


    @Test
    public void testDistances() {
        Point2i one = new Point2i(0, 0);
        Point2i two = new Point2i(10, 20);

        assert 30 == one.getManhattanDistance(two);
        assert 20 == one.getChebyshevDistance(two);

        Point3i one3d = new Point3i(0, 0,0);
        Point3i two3d = new Point3i(10, 20,5);

        assert 35 == one3d.getManhattanDistance(two3d);
        assert 20 == one3d.getChebyshevDistance(two3d);
    }

}
