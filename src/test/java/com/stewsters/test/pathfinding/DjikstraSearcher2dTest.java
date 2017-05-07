package com.stewsters.test.pathfinding;


import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.searcher.DjikstraSearcher2d;
import com.stewsters.util.pathing.twoDimention.searcher.Objective2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import org.junit.Test;

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

        FullPath2d fullPath2d = pathfinder.search(exampleMover2d, 1, 1, objective2d);

        for (int i = 0; i < fullPath2d.getLength(); i++) {
            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
        }

        assertEquals(fullPath2d.getLength(), 15);
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

        FullPath2d fullPath2d = pathfinder.search(exampleMover2d, 1, 1, objective2d);

        for (int i = 0; i < fullPath2d.getLength(); i++) {
            System.out.println("x:" + fullPath2d.getStep(i).getX() + " y:" + fullPath2d.getStep(i).getY());
        }

        assertEquals(fullPath2d.getLength(), 8);
    }

}
