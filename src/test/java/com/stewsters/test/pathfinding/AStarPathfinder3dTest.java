package com.stewsters.test.pathfinding;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.pathing.threeDimention.pathfinder.AStarPathFinder3d;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AStarPathfinder3dTest {

    ExampleCellType floor = new ExampleCellType('.', false);
    ExampleCellType wall = new ExampleCellType('#', true);

    @Test
    public void test6WayPathingTest() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10, floor);

        AStarPathFinder3d pathfinder = new AStarPathFinder3d(map, 100, false);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map);


        FullPath3d fullPath3d = pathfinder.findPath(exampleMover3d, 1, 1, 1, 8, 8, 8);

        for (int i = 0; i < fullPath3d.getLength(); i++) {
            System.out.println("x:" + fullPath3d.getStep(i).getX() + " y:" + fullPath3d.getStep(i).getY() + " z:" + fullPath3d.getStep(i).getZ());
        }

        assertEquals(fullPath3d.getLength(), 22);

    }

    @Test
    public void test26WayPathingTest() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10, floor);

        AStarPathFinder3d pathfinder = new AStarPathFinder3d(map, 1000, true);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map);

        FullPath3d fullPath3d = pathfinder.findPath(exampleMover3d, 1, 1, 1, 8, 8, 8);


        for (int i = 0; i < fullPath3d.getLength(); i++) {
            System.out.println("x:" + fullPath3d.getStep(i).getX() + " y:" + fullPath3d.getStep(i).getY() + " z:" + fullPath3d.getStep(i).getZ());
        }

        assertEquals(fullPath3d.getLength(), 8);

    }

}