package com.stewsters.test.pathfinding;


import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.searcher.DjikstraSearcher2d;
import com.stewsters.util.pathing.twoDimention.searcher.Objective2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DjikstraSearcher2dTest {

    @Test
    public void testFindSomething4Way() {
        ExampleCellType floor = new ExampleCellType('.', false);
        ExampleMap2d map = new ExampleMap2d(10, 10, floor);

        DjikstraSearcher2d pathfinder = new DjikstraSearcher2d(map, 100);

        ExampleMover2d exampleMover2d = new ExampleMover2d(map, new ChebyshevHeuristic2d(), false);

        Objective2d objective2d = new Objective2d() {
            @Override
            public boolean satisfiedBy(PathNode2d current) {
                return (current.x == 8 && current.y == 8);
            }
        };

        List<Point2i> fullPath2d = pathfinder.search(exampleMover2d, 1, 1, objective2d).get();

        for (int i = 0; i < fullPath2d.size(); i++) {
            System.out.println("x:" + fullPath2d.get(i).x + " y:" + fullPath2d.get(i).y);
        }

        assertEquals(fullPath2d.size(), 15);
    }

    @Test
    public void testFindSomething8Way() {
        ExampleCellType floor = new ExampleCellType('.', false);
        ExampleMap2d map = new ExampleMap2d(10, 10, floor);

        DjikstraSearcher2d pathfinder = new DjikstraSearcher2d(map, 100);

        ExampleMover2d exampleMover2d = new ExampleMover2d(map, new ChebyshevHeuristic2d(), true);

        Objective2d objective2d = new Objective2d() {
            @Override
            public boolean satisfiedBy(PathNode2d current) {
                return (current.x == 8 && current.y == 8);
            }
        };

        List<Point2i> fullPath2d = pathfinder.search(exampleMover2d, 1, 1, objective2d).get();

        for (int i = 0; i < fullPath2d.size(); i++) {
            System.out.println("x:" + fullPath2d.get(i).x + " y:" + fullPath2d.get(i).y);
        }

        assertEquals(fullPath2d.size(), 8);
    }

}
