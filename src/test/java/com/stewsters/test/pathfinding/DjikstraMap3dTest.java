package com.stewsters.test.pathfinding;


import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.pathing.threeDimention.djikstraMap.DjikstraMap3d;
import org.junit.Test;

public class DjikstraMap3dTest {

    ExampleCellType floor = new ExampleCellType('.', false);
    ExampleCellType wall = new ExampleCellType('#', true);

    @Test
    public void testOpenMap6Way() {

        ExampleMap3d map = new ExampleMap3d(11, 11, 11, floor);

        DjikstraMap3d djikstraMap3d = new DjikstraMap3d(map, 100, false);
        ExampleMover3d exampleMover3d = new ExampleMover3d(map);

        djikstraMap3d.recalculate(5, 5, 5, exampleMover3d);

        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (int z = 0; z < map.getYSize(); z++) {

                    System.out.print(djikstraMap3d.getDistanceAt(x, y, z) + " ");
                }
            }
            System.out.println();
        }

        assert djikstraMap3d.getDistanceAt(0, 0, 0) == 15f;
        assert djikstraMap3d.getDistanceAt(6, 6, 6) == 3f;
        assert djikstraMap3d.getDistanceAt(7, 5, 6) == 3f;
        assert djikstraMap3d.getDistanceAt(5, 8, 6) == 4f;
        assert djikstraMap3d.getDistanceAt(9, 9, 9) == 12f;

    }


    @Test
    public void testOpenMap26Way() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10, floor);

        DjikstraMap3d djikstraMap3d = new DjikstraMap3d(map, 100, true);
        ExampleMover3d exampleMover3d = new ExampleMover3d(map);

        djikstraMap3d.recalculate(5, 5, 5, exampleMover3d);

        assert djikstraMap3d.getDistanceAt(5, 5, 5) == 0;
        assert djikstraMap3d.getDistanceAt(6, 6, 5) == 1;
        assert djikstraMap3d.getDistanceAt(7, 5, 5) == 2;
        assert djikstraMap3d.getDistanceAt(5, 8, 5) == 3;
        assert djikstraMap3d.getDistanceAt(9, 9, 5) == 4;

    }

}
