package com.stewsters.test.mapgen;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleGeneretedMap2d;
import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.terrain.NoiseFunction2d;
import com.stewsters.util.mapgen.twoDimension.MapGen2d;
import com.stewsters.util.mapgen.twoDimension.brush.DrawCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.AndPredicate2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellEqualAny2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellEquals2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellNearCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellNearEdge2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellNotNearCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.NoiseGreaterThan2d;
import com.stewsters.util.mapgen.twoDimension.predicate.NotPredicate2d;
import com.stewsters.util.mapgen.twoDimension.predicate.OrPredicate2d;
import com.stewsters.util.math.Point2i;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenerateMap2dTest {

    static final ExampleCellType unknown = new ExampleCellType('?', true);
    static final ExampleCellType wall = new ExampleCellType('X', true);
    static final ExampleCellType floor = new ExampleCellType('.', false);
    static final ExampleCellType grass = new ExampleCellType(',', false);

    @Test
    public void testGenerationOfBoxViaPredicates() {

        ExampleGeneretedMap2d em1 = new ExampleGeneretedMap2d(20, 20, unknown);
        MapGen2d.fillWithBorder(em1, floor, wall);

        ExampleGeneretedMap2d em2 = new ExampleGeneretedMap2d(20, 20, unknown);
        MapGen2d.fill(em2, new CellNearEdge2d(), new DrawCell2d(wall));
        MapGen2d.fill(em2, new NotPredicate2d(new CellNearEdge2d()), new DrawCell2d(floor));

        testEquality(em1, em2);
    }

    @Test
    public void testGenerationOfTreesViaPredicates() {

        ExampleGeneretedMap2d em = new ExampleGeneretedMap2d(20, 20, unknown);
        MapGen2d.fill(em, new CellNearEdge2d(), new DrawCell2d(wall));
        MapGen2d.fill(em, new NotPredicate2d(new CellNearEdge2d()), new DrawCell2d(floor));

        System.out.println("Gen trees 1");
        printMap(em);

        NoiseFunction2d vegitation = new NoiseFunction2d(10, 30, 16, 13);
        ExampleCellType tree = new ExampleCellType('T', true);
        MapGen2d.fill(em, new AndPredicate2d(new NoiseGreaterThan2d(vegitation, 0.5), new CellEquals2d(floor)), new DrawCell2d(tree));


        NoiseFunction2d snow = new NoiseFunction2d(-10, -500, 20, 22);
        ExampleCellType pineTree = new ExampleCellType('p', true);
        MapGen2d.fill(em, new AndPredicate2d(new NoiseGreaterThan2d(snow, 0.6), new CellEquals2d(tree)), new DrawCell2d(pineTree));

        System.out.println("Gen trees 2");
        printMap(em);
    }


    @Test
    public void testOr() {
        ExampleGeneretedMap2d em = new ExampleGeneretedMap2d(10, 10, unknown);

        List<CellType> cellTypes = new ArrayList<>();
        cellTypes.add(grass);
        cellTypes.add(floor);

        MapGen2d.fill(em,
                new OrPredicate2d(
                        new CellEqualAny2d(cellTypes),
                        new CellNearCell2d(wall),
                        new CellNotNearCell2d(wall)
                ),
                new DrawCell2d(wall));
    }

    @Test
    public void testFloodFill() {
        ExampleGeneretedMap2d em = new ExampleGeneretedMap2d(10, 10, unknown);

        Point2i center = new Point2i(5, 5);

        List<Point2i> wallPoints = center.mooreNeighborhood();

        //Draw a wall
        MapGen2d.fill(em, (map, x, y) -> wallPoints.contains(new Point2i(x, y)), new DrawCell2d(wall));

        assert em.getCellTypeAt(5, 5) == unknown;

        for (Point2i wallPoint : wallPoints) {
            assert em.getCellTypeAt(wallPoint.x, wallPoint.y) == wall;
        }


        // Floodfill inside that wall and make sure it doesnt escape
        MapGen2d.floodFill(em, center, new CellEquals2d(unknown), (map, x, y) -> {
            map.setCellTypeAt(x, y, floor);
        });

        assert em.getCellTypeAt(5, 5) == floor;
        for (Point2i wallPoint : wallPoints) {
            assert em.getCellTypeAt(wallPoint.x, wallPoint.y) == wall;
        }

        assert em.getCellTypeAt(0, 0) == unknown;

    }


    private void testEquality(ExampleGeneretedMap2d map1, ExampleGeneretedMap2d map2) {

        for (int y = 0; y < map1.getXSize(); y++) {
            for (int x = 0; x < map1.getXSize(); x++) {
                assert map1.getCellTypeAt(x, y) == map2.getCellTypeAt(x, y);
            }
        }
    }

    private void printMap(ExampleGeneretedMap2d exampleGeneretedMap2d) {

        for (int y = 0; y < exampleGeneretedMap2d.getXSize(); y++) {
            for (int x = 0; x < exampleGeneretedMap2d.getXSize(); x++) {

                System.out.print(((ExampleCellType) exampleGeneretedMap2d.getCellTypeAt(x, y)).getGlyph());
            }
            System.out.println();
        }
    }
}
