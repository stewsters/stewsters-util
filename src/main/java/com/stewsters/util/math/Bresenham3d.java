package com.stewsters.util.math;

import java.util.ArrayList;

public class Bresenham3d {

    public static boolean open(int x1, int y1, int z1, int x2, int y2, int z2, Evaluator3d evaluator3d) {

        int i, dx, dy, dz, l, m, n, x_inc, y_inc, z_inc, err_1, err_2, dx2, dy2, dz2;
        int last_point[] = new int[3];
        last_point[0] = x1;
        last_point[1] = y1;
        last_point[2] = z1;

        int point[] = new int[3];
        point[0] = x1;
        point[1] = y1;
        point[2] = z1;
        dx = x2 - x1;
        dy = y2 - y1;
        dz = z2 - z1;
        x_inc = (dx < 0) ? -1 : 1;
        l = Math.abs(dx);
        y_inc = (dy < 0) ? -1 : 1;
        m = Math.abs(dy);
        z_inc = (dz < 0) ? -1 : 1;
        n = Math.abs(dz);
        dx2 = l << 1;
        dy2 = m << 1;
        dz2 = n << 1;

        if ((l >= m) && (l >= n)) {
            err_1 = dy2 - l;
            err_2 = dz2 - l;
            for (i = 0; i < l; i++) {
                if (!evaluator3d.isGood(last_point[0], last_point[1], last_point[2], point[0], point[1], point[2]))
                    return false;

                if (err_1 > 0) {
                    last_point[1] = point[1];
                    point[1] += y_inc;
                    err_1 -= dx2;
                }
                if (err_2 > 0) {
                    last_point[2] = point[2];
                    point[2] += z_inc;
                    err_2 -= dx2;
                }
                err_1 += dy2;
                err_2 += dz2;
                last_point[0] = point[0];
                point[0] += x_inc;
            }
        } else if ((m >= l) && (m >= n)) {
            err_1 = dx2 - m;
            err_2 = dz2 - m;
            for (i = 0; i < m; i++) {
                if (!evaluator3d.isGood(last_point[0], last_point[1], last_point[2], point[0], point[1], point[2]))
                    return false;


                if (err_1 > 0) {
                    last_point[0] = point[0];
                    point[0] += x_inc;
                    err_1 -= dy2;
                }
                if (err_2 > 0) {
                    last_point[2] = point[2];
                    point[2] += z_inc;
                    err_2 -= dy2;
                }
                err_1 += dx2;
                err_2 += dz2;
                last_point[1] = point[1];
                point[1] += y_inc;
            }
        } else {
            err_1 = dy2 - n;
            err_2 = dx2 - n;
            for (i = 0; i < n; i++) {
                if (!evaluator3d.isGood(last_point[0], last_point[1], last_point[2], point[0], point[1], point[2]))
                    return false;


                if (err_1 > 0) {
                    last_point[1] = point[1];
                    point[1] += y_inc;
                    err_1 -= dz2;
                }
                if (err_2 > 0) {
                    last_point[0] = point[0];
                    point[0] += x_inc;
                    err_2 -= dz2;
                }
                err_1 += dy2;
                err_2 += dx2;
                point[2] += z_inc;
            }
        }
        return evaluator3d.isGood(last_point[0], last_point[1], last_point[2], point[0], point[1], point[2]);


    }

    public static ArrayList<Point3i> getArray(int x1, int y1, int z1, int x2, int y2, int z2) {
        ArrayList<Point3i> line = new ArrayList<Point3i>();

        int i, dx, dy, dz, l, m, n, x_inc, y_inc, z_inc, err_1, err_2, dx2, dy2, dz2;
        int point[] = new int[3];

        point[0] = x1;
        point[1] = y1;
        point[2] = z1;
        dx = x2 - x1;
        dy = y2 - y1;
        dz = z2 - z1;
        x_inc = (dx < 0) ? -1 : 1;
        l = Math.abs(dx);
        y_inc = (dy < 0) ? -1 : 1;
        m = Math.abs(dy);
        z_inc = (dz < 0) ? -1 : 1;
        n = Math.abs(dz);
        dx2 = l << 1;
        dy2 = m << 1;
        dz2 = n << 1;

        if ((l >= m) && (l >= n)) {
            err_1 = dy2 - l;
            err_2 = dz2 - l;
            for (i = 0; i < l; i++) {
                line.add(new Point3i(point[0], point[1], point[2]));

                if (err_1 > 0) {
                    point[1] += y_inc;
                    err_1 -= dx2;
                }
                if (err_2 > 0) {
                    point[2] += z_inc;
                    err_2 -= dx2;
                }
                err_1 += dy2;
                err_2 += dz2;
                point[0] += x_inc;
            }
        } else if ((m >= l) && (m >= n)) {
            err_1 = dx2 - m;
            err_2 = dz2 - m;
            for (i = 0; i < m; i++) {
                line.add(new Point3i(point[0], point[1], point[2]));

                if (err_1 > 0) {
                    point[0] += x_inc;
                    err_1 -= dy2;
                }
                if (err_2 > 0) {
                    point[2] += z_inc;
                    err_2 -= dy2;
                }
                err_1 += dx2;
                err_2 += dz2;
                point[1] += y_inc;
            }
        } else {
            err_1 = dy2 - n;
            err_2 = dx2 - n;
            for (i = 0; i < n; i++) {
                line.add(new Point3i(point[0], point[1], point[2]));

                if (err_1 > 0) {
                    point[1] += y_inc;
                    err_1 -= dz2;
                }
                if (err_2 > 0) {
                    point[0] += x_inc;
                    err_2 -= dz2;
                }
                err_1 += dy2;
                err_2 += dx2;
                point[2] += z_inc;
            }
        }
        line.add(new Point3i(point[0], point[1], point[2]));

        return line;
    }

}