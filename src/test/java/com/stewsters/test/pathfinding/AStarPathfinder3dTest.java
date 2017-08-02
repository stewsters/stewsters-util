package com.stewsters.test.pathfinding;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.ChebyshevHeuristic3d;
import com.stewsters.util.pathing.threeDimention.heuristic.ManhattanHeuristic3d;
import com.stewsters.util.pathing.threeDimention.pathfinder.AStarPathFinder3d;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AStarPathfinder3dTest {

    ExampleCellType floor = new ExampleCellType('.', false);
    ExampleCellType wall = new ExampleCellType('#', true);

    @Test
    public void test6WayPathingTest() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10, floor);

        AStarPathFinder3d pathfinder = new AStarPathFinder3d(map, 100, false);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map, new ManhattanHeuristic3d(), false);


        List<Point3i> fullPath3d = pathfinder.findPath(exampleMover3d, 1, 1, 1, 8, 8, 8).get();

        for (int i = 0; i < fullPath3d.size(); i++) {
            System.out.println("x:" + fullPath3d.get(i).x + " y:" + fullPath3d.get(i).y + " z:" + fullPath3d.get(i).z);
        }

        assertEquals(fullPath3d.size(), 22);

    }

    @Test
    public void test26WayPathingTest() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10, floor);

        AStarPathFinder3d pathfinder = new AStarPathFinder3d(map, 1000, true);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map, new ChebyshevHeuristic3d(), true);

        List<Point3i> fullPath3d = pathfinder.findPath(exampleMover3d, 1, 1, 1, 8, 8, 8).get();


        for (int i = 0; i < fullPath3d.size(); i++) {
            System.out.println("x:" + fullPath3d.get(i).x + " y:" + fullPath3d.get(i).y + " z:" + fullPath3d.get(i).x);
        }

        assertEquals(fullPath3d.size(), 8);

    }

}