package com.stewsters.test;


import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.pathing.threeDimention.searcher.DjikstraSearcher3d;
import com.stewsters.util.pathing.threeDimention.searcher.Objective3d;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DjikstraSearcher3dTest {


    @Test
    public void testFindSomething4Way() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10);

        DjikstraSearcher3d pathfinder = new DjikstraSearcher3d(map, 100, false);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map);

        Objective3d objective3d = new Objective3d() {
            @Override
            public boolean satisfiedBy(PathNode3d current) {
                return (current.x == 8 && current.y == 8);
            }
        };

        FullPath3d fullPath3d = pathfinder.search(exampleMover3d, 1, 1, 1, objective3d);

        for (int i = 0; i < fullPath3d.getLength(); i++) {
            System.out.println("x:" + fullPath3d.getStep(i).getX() + " y:" + fullPath3d.getStep(i).getY() + " z:" + fullPath3d.getStep(i).getZ());
        }

        assertEquals(fullPath3d.getLength(), 15);
    }

    @Test
    public void testFindSomething8Way() {

        ExampleMap3d map = new ExampleMap3d(10, 10, 10);

        DjikstraSearcher3d pathfinder = new DjikstraSearcher3d(map, 100, true);

        ExampleMover3d exampleMover3d = new ExampleMover3d(map);

        Objective3d objective3d = new Objective3d() {
            @Override
            public boolean satisfiedBy(PathNode3d current) {
                return (current.x == 8 && current.y == 8);
            }
        };

        FullPath3d fullPath3d = pathfinder.search(exampleMover3d, 1, 1, 1, objective3d);

        for (int i = 0; i < fullPath3d.getLength(); i++) {
            System.out.println("x:" + fullPath3d.getStep(i).getX() + " y:" + fullPath3d.getStep(i).getY() + " z:" + fullPath3d.getStep(i).getZ());
        }

        assertEquals(fullPath3d.getLength(), 8);
    }

}
