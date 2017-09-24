package com.stewsters.test.pathfinding;


import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.searcher.DjikstraSearcher3d;
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

        DjikstraSearcher3d pathfinder = new DjikstraSearcher3d(map, 100);

        List<Point3i> fullPath3d = pathfinder.search(
                (PathNode3d current) -> (current.x == 8 && current.y == 8),
                (int sx, int sy, int sz, int tx, int ty, int tz) -> !map.isBlocked(tx, ty, tz),
                (int sx, int sy, int sz, int tx, int ty, int tz) -> 1.0f,
                false,
                1, 1, 1).get();

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

        DjikstraSearcher3d pathfinder = new DjikstraSearcher3d(map, 1000);

        List<Point3i> fullPath3d = pathfinder.search(
                (PathNode3d current) -> (current.x == 52 && current.y == 52 && current.z == 50),
                (int sx, int sy, int sz, int tx, int ty, int tz) -> !map.isBlocked(tx, ty, tz),
                (int sx, int sy, int sz, int tx, int ty, int tz) -> 1.0f,
                true,
                50, 50, 50).get();

        for (int i = 0; i < fullPath3d.size(); i++) {
            System.out.println("x:" + fullPath3d.get(i).x + " y:" + fullPath3d.get(i).y + " z:" + fullPath3d.get(i).z);
        }

        assertEquals(3, fullPath3d.size());
    }

}
