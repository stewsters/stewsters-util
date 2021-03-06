package com.stewsters.test.pathfinding;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.heuristic.ClosestHeuristic2d;
import com.stewsters.util.pathing.twoDimention.heuristic.ManhattanHeuristic2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AStarPathfinder2dTest {

    ExampleCellType floor = new ExampleCellType('.', false);
    ExampleCellType wall = new ExampleCellType('#', true);

    @Test
    public void test4WayPathingTest() {
        System.out.println("Test 4 way path");

        ExampleMap2d map = new ExampleMap2d(10, 10, floor);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100);

        List<Point2i> fullPath2d = pathfinder.findPath(
                (int sx, int sy, int tx, int ty) -> !map.isBlocked(tx, ty),
                (int tx, int ty) -> !map.isBlocked(tx, ty),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                new ManhattanHeuristic2d(),
                false,
                1, 1, 8, 8).get();

        for (int i = 0; i < fullPath2d.size(); i++) {
            System.out.println("x:" + fullPath2d.get(i).x + " y:" + fullPath2d.get(i).y);
        }

        assertEquals(15, fullPath2d.size());

    }


    @Test
    public void test4WayFatPathingTest() {
        System.out.println("Test 4 way path with fat enemy");

        ExampleMap2d map = new ExampleMap2d(10, 10, floor);

        for (int y = 0; y < 8; y++) {
            map.ground[5][y] = wall;
        }
        map.ground[5][2] = floor;

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100);

        int xSize = 2;
        int ySize = 2;

        List<Point2i> fullPath2d = pathfinder.findPath(
                (int sx, int sy, int tx, int ty) -> {
                    for (int x = 0; x < xSize; x++) {
                        for (int y = 0; y < ySize; y++) {
                            if (map.isBlocked(tx + x, ty + y)) {
                                return false;
                            }
                        }
                    }
                    return true;
                },
                (int tx, int ty) -> !map.isBlocked(tx, ty),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                new ManhattanHeuristic2d(),
                false,
                1, 1, 8, 1).get();

        for (int i = 0; i < fullPath2d.size(); i++) {
            System.out.println("x:" + fullPath2d.get(i).x + " y:" + fullPath2d.get(i).y);
        }


        assertEquals(22, fullPath2d.size());

    }

    @Test
    public void test8WayPathingTest() {
        System.out.println("Test 8 way path");

        ExampleMap2d map = new ExampleMap2d(10, 10, floor);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100);

        List<Point2i> fullPath2d = pathfinder.findPath(
                (int sx, int sy, int tx, int ty) -> !map.isBlocked(tx, ty),
                (int tx, int ty) -> !map.isBlocked(tx, ty),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                new ChebyshevHeuristic2d(),
                true,
                1, 1, 8, 8).get();

        for (int i = 0; i < fullPath2d.size(); i++) {
            System.out.println("x:" + fullPath2d.get(i).x + " y:" + fullPath2d.get(i).y);
        }

        assertEquals(8, fullPath2d.size());

    }


    @Test
    public void test8WayPathingTest2() {
        System.out.println("Test 8 way path");

        ExampleMap2d map = new ExampleMap2d(10, 20, floor);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 100);

        List<Point2i> fullPath2d = pathfinder.findPath(
                (int sx, int sy, int tx, int ty) -> !map.isBlocked(tx, ty),
                (int tx, int ty) -> !map.isBlocked(tx, ty),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                new ClosestHeuristic2d(),
                true,
                1, 1, 9, 19).get();

        for (int i = 0; i < fullPath2d.size(); i++) {
            System.out.println("x:" + fullPath2d.get(i).x + " y:" + fullPath2d.get(i).y);
        }

        assertEquals(19, fullPath2d.size());

    }


    @Test
    public void testHugePathfinding() {
        System.out.println("Test huge path");

        ExampleMap2d map = new ExampleMap2d(2000, 2000, floor);

        AStarPathFinder2d pathfinder = new AStarPathFinder2d(map, 1000000);

        List<Point2i> fullPath2d = pathfinder.findPath(
                (int sx, int sy, int tx, int ty) -> !map.isBlocked(tx, ty),
                (int tx, int ty) -> !map.isBlocked(tx, ty),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                new ChebyshevHeuristic2d(),
                true,
                1, 1, 1999, 1999).get();

//        for (int i = 0; i < fullPath2d.getLength(); i++) {
//            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
//        }

        assertEquals(1999, fullPath2d.size());

    }
}