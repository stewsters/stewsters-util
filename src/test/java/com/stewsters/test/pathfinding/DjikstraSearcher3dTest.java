package com.stewsters.test.pathfinding;


import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.ChebyshevHeuristic3d;
import com.stewsters.util.pathing.threeDimention.heuristic.ManhattanHeuristic3d;
import com.stewsters.util.pathing.threeDimention.searcher.DjikstraSearcher3d;
import com.stewsters.util.pathing.threeDimention.searcher.Objective3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DjikstraSearcher3dTest {

    ExampleCellType floor = new ExampleCellType('.', false);
    ExampleCellType wall = new ExampleCellType('#', true);

    @Test
    public void testFindSomething4Way() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10, floor);

        DjikstraSearcher3d pathfinder = new DjikstraSearcher3d(map, 100, false);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map, new ManhattanHeuristic3d(), false);

        Objective3d objective3d = new Objective3d() {
            @Override
            public boolean satisfiedBy(PathNode3d current) {
                return (current.x == 8 && current.y == 8);
            }
        };

        List<Point3i> fullPath3d = pathfinder.search(exampleMover3d, 1, 1, 1, objective3d).get();

        for (int i = 0; i < fullPath3d.size(); i++) {
            System.out.println("x:" + fullPath3d.get(i).x + " y:" + fullPath3d.get(i).y + " z:" + fullPath3d.get(i).z);
        }

        assertEquals(fullPath3d.size(), 15);
    }

    @Test
    public void testFindSomething8Way() {

        ExampleMap3d map = new ExampleMap3d(100, 100, 100, floor);

        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (int z = 0; z < map.getZSize(); z++) {
                    map.setTile(x, y, z, wall);
                }
            }
        }

        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                map.setTile(50 + x, 50 + y, 50, floor);
            }
        }


        DjikstraSearcher3d pathfinder = new DjikstraSearcher3d(map, 1000, true);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map, new ChebyshevHeuristic3d(), true);

        Objective3d objective3d = new Objective3d() {
            @Override
            public boolean satisfiedBy(PathNode3d current) {
                return (current.x == 52 && current.y == 52 && current.z == 50);
            }
        };

        List<Point3i> fullPath3d = pathfinder.search(exampleMover3d, 50, 50, 50, objective3d).get();

        for (int i = 0; i < fullPath3d.size(); i++) {
            System.out.println("x:" + fullPath3d.get(i).x + " y:" + fullPath3d.get(i).y + " z:" + fullPath3d.get(i).z);
        }

        assertEquals(3, fullPath3d.size());
    }

}
