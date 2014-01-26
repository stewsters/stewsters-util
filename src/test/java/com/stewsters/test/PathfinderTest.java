package com.stewsters.test;

import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PathfinderTest {

    @Test
    public void test4WayPathingTest() {

        ExampleMap2d map = new ExampleMap2d(10, 10);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100, false);

        ExampleMover exampleMover = new ExampleMover(map);

        FullPath2d fullPath2d = pathfinder.findPath(exampleMover, 1, 1, 8, 8);

        for (int i = 0; i < fullPath2d.getLength(); i++) {
            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
        }


        assertEquals(fullPath2d.getLength(), 15);

    }

    @Test
    public void test8WayPathingTest() {

        ExampleMap2d map = new ExampleMap2d(10, 10);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100, true);

        ExampleMover exampleMover = new ExampleMover(map);

        FullPath2d fullPath2d = pathfinder.findPath(exampleMover, 1, 1, 8, 8);


        for (int i = 0; i < fullPath2d.getLength(); i++) {
            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
        }

        assertEquals(fullPath2d.getLength(), 8);

    }

}