package com.stewsters.test;

import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;

public class AStarPathfinder2dTest {

    @Test
    public void test4WayPathingTest() {

        ExampleMap2d map = new ExampleMap2d(10, 10);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100, false);

        ExampleMover2d exampleMover2d = new ExampleMover2d(map);

        FullPath2d fullPath2d = pathfinder.findPath(exampleMover2d, 1, 1, 8, 8);

        for (int i = 0; i < fullPath2d.getLength(); i++) {
            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
        }


        assertEquals(fullPath2d.getLength(), 15);

    }

    @Test
    public void test8WayPathingTest() {

        ExampleMap2d map = new ExampleMap2d(10, 10);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100, true);

        ExampleMover2d exampleMover2d = new ExampleMover2d(map);

        FullPath2d fullPath2d = pathfinder.findPath(exampleMover2d, 1, 1, 8, 8);


        for (int i = 0; i < fullPath2d.getLength(); i++) {
            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
        }

        assertEquals(fullPath2d.getLength(), 8);

    }

    @Test
    public void testFindNearest(){
        ExampleMap2d map = new ExampleMap2d(10, 10);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100, true);

        ExampleMover2d exampleMover2d = new ExampleMover2d(map);

        LinkedList<Point2i> results =  pathfinder.getReachableCells(exampleMover2d, 4, 4, 16);

        System.out.println("find nearest");
        for (Point2i result : results) {
            System.out.println(result.x + " "+ result.y );
        }

        assertEquals(64, results.size());

    }

}