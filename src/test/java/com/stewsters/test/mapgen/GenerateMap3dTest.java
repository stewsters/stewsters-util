package com.stewsters.test.mapgen;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleGeneretedMap3d;
import com.stewsters.util.mapgen.terrain.NoiseFunction3d;
import com.stewsters.util.mapgen.threeDimension.MapGen3d;
import com.stewsters.util.mapgen.threeDimension.brush.DrawCell3d;
import com.stewsters.util.mapgen.threeDimension.predicate.AndPredicate3d;
import com.stewsters.util.mapgen.threeDimension.predicate.CellEquals3d;
import com.stewsters.util.mapgen.threeDimension.predicate.CellNearEdge3d;
import com.stewsters.util.mapgen.threeDimension.predicate.NoiseGreaterThan3d;
import com.stewsters.util.mapgen.threeDimension.predicate.NotPredicate3d;
import org.junit.Test;

public class GenerateMap3dTest {

    @Test
    public void testGenerationOfBoxViaPredicates() {
        ExampleCellType unknown = new ExampleCellType('?', true);
        ExampleCellType wall = new ExampleCellType('X', true);
        ExampleCellType floor = new ExampleCellType('.', false);

        ExampleGeneretedMap3d em1 = new ExampleGeneretedMap3d(20, 20, 20, unknown);
        MapGen3d.fillWithBorder(em1, floor, wall);


        ExampleGeneretedMap3d em2 = new ExampleGeneretedMap3d(20, 20, 20, unknown);
        MapGen3d.fill(em2, new CellNearEdge3d(), new DrawCell3d(wall));
        MapGen3d.fill(em2, new NotPredicate3d(new CellNearEdge3d()), new DrawCell3d(floor));


        testEquality(em1, em2);

//        System.out.println("Gen");
//        printMap(em1);
    }

    @Test
    public void testGenerationOfTreesViaPredicates() {
        ExampleCellType unknown = new ExampleCellType('?', true);
        ExampleCellType wall = new ExampleCellType('X', true);
        ExampleCellType floor = new ExampleCellType('.', false);


        ExampleGeneretedMap3d em = new ExampleGeneretedMap3d(20, 20, 20, unknown);
        MapGen3d.fill(em, new CellNearEdge3d(), new DrawCell3d(wall));
        MapGen3d.fill(em, new NotPredicate3d(new CellNearEdge3d()), new DrawCell3d(floor));

        System.out.println("Gen trees 1");
        printMap(em);

        NoiseFunction3d vegitation = new NoiseFunction3d(10, 30, 10, 16, 13, 32);
        ExampleCellType tree = new ExampleCellType('T', true);
        MapGen3d.fill(em, new AndPredicate3d(new NoiseGreaterThan3d(vegitation, 0.5), new CellEquals3d(floor)), new DrawCell3d(tree));


        NoiseFunction3d snow = new NoiseFunction3d(-10, -500, 20, 20, 22, 11);
        ExampleCellType pineTree = new ExampleCellType('p', true);
        MapGen3d.fill(em, new AndPredicate3d(new NoiseGreaterThan3d(snow, 0.6), new CellEquals3d(tree)), new DrawCell3d(pineTree));


        System.out.println("Gen trees 2");
        printMap(em);
    }


    private void testEquality(ExampleGeneretedMap3d map1, ExampleGeneretedMap3d map2) {

        for (int y = 0; y < map1.getXSize(); y++) {
            for (int x = 0; x < map1.getXSize(); x++) {
                for (int z = 0; z < map1.getXSize(); z++) {
                    assert map1.getCellTypeAt(x, y, z) == map2.getCellTypeAt(x, y, z);
                }
            }
        }
    }

    private void printMap(ExampleGeneretedMap3d exampleGeneretedMap3d) {

        for (int y = 0; y < exampleGeneretedMap3d.getXSize(); y++) {
            for (int x = 0; x < exampleGeneretedMap3d.getXSize(); x++) {
                for (int z = 0; z < exampleGeneretedMap3d.getZSize(); z++) {

                    System.out.print(((ExampleCellType) exampleGeneretedMap3d.getCellTypeAt(x, y, z)).getGlyph());
                }
                System.out.println();
            }
            System.out.println();
        }
    }
}
