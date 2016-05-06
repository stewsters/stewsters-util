package com.stewsters.test.spatial;

import com.stewsters.test.examples.Spacecraft2d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.spatial.IntervalKDTree2d;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class IntervalKDTree2dTest {

    @Test
    public void testCollision() {
        final int range = 100;
        final int quantity = 10;

        IntervalKDTree2d<Spacecraft2d> spacecrafts = new IntervalKDTree2d<Spacecraft2d>(range, 10);

        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            Spacecraft2d spacecraft = new Spacecraft2d(random.nextInt(2 * range) - range, random.nextInt(2 * range) - range);
            spacecraft.addToTree(spacecrafts);

        }

        HashSet<Spacecraft2d> fillThis = new HashSet<Spacecraft2d>();

        long nanoTime = System.nanoTime();
        fillThis = spacecrafts.getValues(-50, -50, 50, 50, fillThis);
        long finishTime = System.nanoTime();

        List<Spacecraft2d> fillThisToo = new LinkedList<Spacecraft2d>();
        spacecrafts.getValues(-50, -50, 50, 50, fillThisToo);

        assert fillThis.size() == fillThisToo.size();


        for (Spacecraft2d spacecraft : fillThis) {
            System.out.println(spacecraft.toString());

        }

        System.out.println(((double) finishTime - nanoTime) / 1000000000);


        nanoTime = System.nanoTime();
        for (Spacecraft2d spacecraft : fillThis) {
            spacecrafts.remove(spacecraft);
            spacecraft.x++;
            spacecraft.y++;

            spacecraft.addToTree(spacecrafts);
        }

        finishTime = System.nanoTime();
        System.out.println(((double) finishTime - nanoTime) / 1000000000);

    }


    @Test
    public void testCorners() {
        IntervalKDTree2d<GridThing> kdTree = new IntervalKDTree2d<GridThing>(100, 10);

        GridThing e = new GridThing();
        e.x = 10;
        e.y = 20;
        e.xSize = 2;
        e.ySize = 2;

        kdTree.put(e.x - 0.25, e.y - 0.25, e.x + e.xSize - 0.75, e.y + e.ySize - 0.75, e);

//        entityTemp.clear()


        ArrayList<Point2i> pointsInside = new ArrayList<>();
        pointsInside.add(new Point2i(e.x, e.y));
        pointsInside.add(new Point2i(e.x + 1, e.y));
        pointsInside.add(new Point2i(e.x, e.y + 1));
        pointsInside.add(new Point2i(e.x + 1, e.y + 1));


        ArrayList<GridThing> results = new ArrayList<>();
        for (Point2i p : pointsInside) {
            results.clear();
            if(!kdTree.getValues(p.x - 0.5, p.y - 0.5, p.x + 0.5, p.y + 0.5, results).contains(e)){
                System.out.println(p);
//                assert false;
            }

        }


        ArrayList<Point2i> pointsOutside = new ArrayList<>();
        pointsOutside.add(new Point2i(e.x-1, e.y));
        pointsOutside.add(new Point2i(e.x - 1, e.y-1));
        pointsOutside.add(new Point2i(e.x, e.y + 2));
        pointsOutside.add(new Point2i(e.x + 2, e.y + 2));


        for (Point2i p : pointsOutside) {
            results.clear();
            assert !kdTree.getValues(p.x - 0.5, p.y - 0.5, p.x + 0.5, p.y + 0.5, results).contains(e);
        }

//        assert gridthings.getValues()


    }

    class GridThing {
        int x, y;
        int xSize, ySize;
    }

}
