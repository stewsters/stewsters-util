package com.stewsters.util.math;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point2i {

    public int x;
    public int y;

    public Point2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Point2i))
            return false;
        Point2i other = (Point2i) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }

    public List<Point2i> mooreNeighborhood() {
        List<Point2i> points = new ArrayList<Point2i>();

        for (int ox = -1; ox < 2; ox++) {
            for (int oy = -1; oy < 2; oy++) {
                if (ox == 0 && oy == 0)
                    continue;

                points.add(new Point2i(x + ox, y + oy));
            }
        }

        Collections.shuffle(points);
        return points;
    }

    int[] zeroOrOne = {-1, 1};

    public List<Point2i> vonNeumannNeighborhood() {
        List<Point2i> points = new ArrayList<Point2i>();

        for (int ox : zeroOrOne) {
            for (int oy : zeroOrOne) {
                points.add(new Point2i(x + ox, y + oy));
            }
        }

        Collections.shuffle(points);
        return points;
    }


    public int getManhattanDistance(Point2i other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    public int getManhattanDistance(int x, int y) {
        return Math.abs(this.x - x) + Math.abs(this.y - y);
    }

    // Manhattan distance with diagonals
    public int getChebyshevDistance(Point2i other) {
        return Math.max(Math.abs(this.x - other.x), Math.abs(this.y - other.y));
    }

    public int getChebyshevDistance(int x, int y) {
        return Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
    }


    @Override
    public String toString() {
        return (x + " " + y);
    }


}
