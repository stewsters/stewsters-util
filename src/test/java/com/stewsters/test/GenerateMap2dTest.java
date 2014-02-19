package com.stewsters.test;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleGeneretedMap2d;
import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.terrain.NoiseFunction;
import com.stewsters.util.mapgen.twoDimension.MapGen2d;
import com.stewsters.util.mapgen.twoDimension.brush.DrawCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.*;
import org.junit.Test;

public class GenerateMap2dTest {

    @Test
    public void testGenerationOfBoxViaPredicates() {
        CellType unknown = new ExampleCellType('?', true);
        CellType wall = new ExampleCellType('X', true);
        CellType floor = new ExampleCellType('.', false);

        ExampleGeneretedMap2d em1 = new ExampleGeneretedMap2d(20, 20, unknown);
        MapGen2d.fillWithBorder(em1, floor, wall);


        ExampleGeneretedMap2d em2 = new ExampleGeneretedMap2d(20, 20, unknown);
        MapGen2d.fill(em2, new CellNearEdge2d(), new DrawCell2d(wall));
        MapGen2d.fill(em2, new NotPredicate2d(new CellNearEdge2d()), new DrawCell2d(floor));


        testEquality(em1, em2);

//        System.out.println("Gen");
//        printMap(em1);
    }

    @Test
    public void testGenerationOfTreesViaPredicates() {
        CellType unknown = new ExampleCellType('?', true);
        CellType wall = new ExampleCellType('X', true);
        CellType floor = new ExampleCellType('.', false);


        ExampleGeneretedMap2d em = new ExampleGeneretedMap2d(20, 20, unknown);
        MapGen2d.fill(em, new CellNearEdge2d(), new DrawCell2d(wall));
        MapGen2d.fill(em, new NotPredicate2d(new CellNearEdge2d()), new DrawCell2d(floor));

        System.out.println("Gen trees 1");
        printMap(em);

        NoiseFunction vegitation = new NoiseFunction(10, 30, 16, 13);
        CellType tree = new ExampleCellType('T', true);
        MapGen2d.fill(em, new AndPredicate2d(new NoiseGreaterThan(vegitation, 0.5), new CellEquals2d(floor)), new DrawCell2d(tree));


        NoiseFunction snow = new NoiseFunction(-10, -500, 20, 22);
        CellType pineTree = new ExampleCellType('p', true);
        MapGen2d.fill(em, new AndPredicate2d(new NoiseGreaterThan(snow, 0.6), new CellEquals2d(tree)), new DrawCell2d(pineTree));


        System.out.println("Gen trees 2");
        printMap(em);
    }


    private void testEquality(ExampleGeneretedMap2d map1, ExampleGeneretedMap2d map2) {

        for (int y = 0; y < map1.getWidthInTiles(); y++) {
            for (int x = 0; x < map1.getWidthInTiles(); x++) {
                assert map1.getCellTypeAt(x, y) == map2.getCellTypeAt(x, y);
            }
        }
    }

    private void printMap(ExampleGeneretedMap2d exampleGeneretedMap2d) {

        for (int y = 0; y < exampleGeneretedMap2d.getWidthInTiles(); y++) {
            for (int x = 0; x < exampleGeneretedMap2d.getWidthInTiles(); x++) {

                System.out.print(exampleGeneretedMap2d.getCellTypeAt(x, y).getGlyph());
            }
            System.out.println();
        }
    }
}
