package com.stewsters.test;


import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.util.pathing.twoDimention.djikstraMap.DjikstraMap2d;
import org.junit.Test;

public class DjikstraMap2dTest {

    ExampleCellType floor = new ExampleCellType('.', false);
    ExampleCellType wall = new ExampleCellType('#', true);

    @Test
    public void testOpenMap4Way() {

        ExampleMap2d map = new ExampleMap2d(11, 11, floor);

        DjikstraMap2d djikstraMap2d = new DjikstraMap2d(map, 100, false);
        ExampleMover2d exampleMover2d = new ExampleMover2d(map);

        djikstraMap2d.recalculate(5, 5, exampleMover2d);

        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                System.out.print(djikstraMap2d.getDistanceAt(x, y) + " ");
            }
            System.out.println();
        }

        assert djikstraMap2d.getDistanceAt(0, 0) == 10;
        assert djikstraMap2d.getDistanceAt(6, 6) == 2;
        assert djikstraMap2d.getDistanceAt(7, 5) == 2;
        assert djikstraMap2d.getDistanceAt(5, 8) == 3;
        assert djikstraMap2d.getDistanceAt(9, 9) == 8;

    }


    @Test
    public void testOpenMap8Way() {

        ExampleMap2d map = new ExampleMap2d(10, 10, floor);

        DjikstraMap2d djikstraMap2d = new DjikstraMap2d(map, 100, true);
        ExampleMover2d exampleMover2d = new ExampleMover2d(map);

        djikstraMap2d.recalculate(5, 5, exampleMover2d);

        assert djikstraMap2d.getDistanceAt(5, 5) == 0;
        assert djikstraMap2d.getDistanceAt(6, 6) == 1;
        assert djikstraMap2d.getDistanceAt(7, 5) == 2;
        assert djikstraMap2d.getDistanceAt(5, 8) == 3;
        assert djikstraMap2d.getDistanceAt(9, 9) == 4;

    }

}
