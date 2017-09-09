package com.stewsters.test.mapgen;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleGeneratedMap2d;
import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.terrain.NoiseFunction2d;
import com.stewsters.util.mapgen.twoDimension.brush.DrawCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.AndPredicate2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellContainedIn2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellEqualAny2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellEquals2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellNearCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellNearEdge2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellNotNearCell2d;
import com.stewsters.util.mapgen.twoDimension.predicate.NoiseGreaterThan2d;
import com.stewsters.util.mapgen.twoDimension.predicate.NotPredicate2d;
import com.stewsters.util.mapgen.twoDimension.predicate.OrPredicate2d;
import com.stewsters.util.math.MatUtils;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.geom.Rect;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.heuristic.ManhattanHeuristic2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.PathFinder2d;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.stewsters.util.mapgen.twoDimension.MapGen2d.fill;
import static com.stewsters.util.mapgen.twoDimension.MapGen2d.fillWithBorder;
import static com.stewsters.util.mapgen.twoDimension.MapGen2d.floodFill;
import static com.stewsters.util.math.MatUtils.euclideanDistance;
import static com.stewsters.util.math.MatUtils.getIntInRange;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class GenerateMap2dTest {

    static final ExampleCellType unknown = new ExampleCellType('?', true);
    static final ExampleCellType wall = new ExampleCellType('X', true);
    static final ExampleCellType floor = new ExampleCellType('.', false);
    static final ExampleCellType grass = new ExampleCellType(',', false);
    static final ExampleCellType forest = new ExampleCellType('T', true);
    static final ExampleCellType water = new ExampleCellType('w', true);
    static final ExampleCellType door = new ExampleCellType('D', false);

    @Test
    public void testGenerationOfBoxViaPredicates() {

        ExampleGeneratedMap2d em1 = new ExampleGeneratedMap2d(20, 20, unknown);
        fillWithBorder(em1, floor, wall);

        ExampleGeneratedMap2d em2 = new ExampleGeneratedMap2d(20, 20, unknown);
        fill(em2, new CellNearEdge2d(), new DrawCell2d(wall));
        fill(em2, new NotPredicate2d(new CellNearEdge2d()), new DrawCell2d(floor));

        testEquality(em1, em2);
        printMap(em1);
        printMap(em2);
    }

    @Test
    public void testGenerationOfTreesViaPredicates() {

        ExampleGeneratedMap2d em = new ExampleGeneratedMap2d(20, 20, unknown);
        fill(em, new CellNearEdge2d(), new DrawCell2d(wall));
        fill(em, new NotPredicate2d(new CellNearEdge2d()), new DrawCell2d(floor));

        System.out.println("Gen trees 1");
        printMap(em);

        NoiseFunction2d vegetation = new NoiseFunction2d(10, 30, 16, 13);
        ExampleCellType tree = new ExampleCellType('T', true);
        fill(em, new AndPredicate2d(new NoiseGreaterThan2d(vegetation, 0.5), new CellEquals2d(floor)), new DrawCell2d(tree));

        NoiseFunction2d snow = new NoiseFunction2d(-10, -500, 20, 22);
        ExampleCellType pineTree = new ExampleCellType('p', true);
        fill(em, new AndPredicate2d(new NoiseGreaterThan2d(snow, 0.6), new CellEquals2d(tree)), new DrawCell2d(pineTree));

        System.out.println("Gen trees 2");
        printMap(em);
    }


    @Test
    public void testOr() {
        ExampleGeneratedMap2d em = new ExampleGeneratedMap2d(10, 10, unknown);

        List<CellType> cellTypes = new ArrayList<>();
        cellTypes.add(grass);
        cellTypes.add(floor);

        fill(em,
                new OrPredicate2d(
                        new CellEqualAny2d(cellTypes),
                        new CellNearCell2d(wall),
                        new CellNotNearCell2d(wall)
                ),
                new DrawCell2d(wall));

        printMap(em);
    }

    @Test
    public void testFloodFill() {
        ExampleGeneratedMap2d em = new ExampleGeneratedMap2d(10, 10, unknown);

        Point2i center = new Point2i(5, 5);

        List<Point2i> wallPoints = center.mooreNeighborhood();

        //Draw a wall
        fill(em, (map, x, y) -> wallPoints.contains(new Point2i(x, y)), new DrawCell2d(wall));

        assert em.getCellTypeAt(5, 5) == unknown;

        for (Point2i wallPoint : wallPoints) {
            assert em.getCellTypeAt(wallPoint.x, wallPoint.y) == wall;
        }


        // Floodfill inside that wall and make sure it doesnt escape
        floodFill(em, center, new CellEquals2d(unknown), (map, x, y) -> {
            map.setCellTypeAt(x, y, floor);
        });

        assert em.getCellTypeAt(5, 5) == floor;

        for (Point2i wallPoint : wallPoints) {
            assert em.getCellTypeAt(wallPoint.x, wallPoint.y) == wall;
        }

        assert em.getCellTypeAt(0, 0) == unknown;

        printMap(em);
    }


    @Test
    public void makeAVillageByALake() {
        ExampleGeneratedMap2d em = new ExampleGeneratedMap2d(100, 100, unknown);

        fillWithBorder(em, grass, forest);

        int xMid = em.getXSize() / 2;
        int yMid = em.getYSize() / 2;
        double buildingRadius = xMid / 2;
        double lakeRadius = buildingRadius / 2;
        double forestRadius = buildingRadius + 1;

        List<Rect> rooms = IntStream.range(0, 10).mapToObj(i -> {
            float angle = MatUtils.getFloatInRange(0, (float) Math.PI * 2);
            int x = xMid + (int) (buildingRadius * cos(angle)) + getIntInRange(-3, 3);
            int y = yMid + (int) (buildingRadius * sin(angle)) + getIntInRange(-3, 3);

            int xw = getIntInRange(2, 3);
            int yw = getIntInRange(2, 3);

            return new Rect(x - xw, y - yw, x + xw, y + yw);
        }).collect(Collectors.toList());

        rooms.forEach(room -> fill(em,
                new CellContainedIn2d(room),
                new DrawCell2d(floor)
        ));

        //Put walls around buildings
        fill(em,
                new AndPredicate2d(
                        new CellNearCell2d(floor),
                        new CellEquals2d(grass)
                ), new DrawCell2d(wall));

        // Fill in pond
        NoiseFunction2d lake = new NoiseFunction2d(30, 20, 24, 24);
        fill(em, new AndPredicate2d(
                new CellEquals2d(grass),
                (e, x, y) -> (0.1) * lake.gen(x, y) + (0.9) * ((lakeRadius - euclideanDistance(xMid, yMid, x, y)) / (float) xMid) > 0
        ), new DrawCell2d(water));


        NoiseFunction2d trees = new NoiseFunction2d(10, 30, 4, 4);
        fill(em, new AndPredicate2d(
                new CellEquals2d(grass),
                (e, x, y) -> trees.gen(x, y) + ((forestRadius - euclideanDistance(xMid, yMid, x, y)) / (float) xMid) < 0
        ), new DrawCell2d(forest));

        // Dig paths
        List<Point2i> centers = rooms.stream().map(room -> room.center()).collect(Collectors.toList());

        PathFinder2d pathFinder2d = new AStarPathFinder2d(em, 200);

        int xCenterLast = xMid;
        int yCenterLast = 0;

        AStarHeuristic2d aStarHeuristic2d = new ManhattanHeuristic2d();
        Mover2d mover = new Mover2d() {
            @Override
            public boolean canTraverse(int sx, int sy, int tx, int ty) {
                return true;
            }

            @Override
            public boolean canOccupy(int tx, int ty) {
                return true;
            }

            @Override
            public float getCost(int sx, int sy, int tx, int ty) {

                CellType cellType = em.getCellTypeAt(tx, ty);

                if (cellType == wall)
                    return 10;
                else if (cellType == floor)
                    return 2;
                else if (cellType == grass)
                    return 1;
                else if (cellType == forest)
                    return 2;
                else if (cellType == water)
                    return 30;
                return 1;
            }

            @Override
            public AStarHeuristic2d getHeuristic() {
                return aStarHeuristic2d;
            }

            @Override
            public boolean getDiagonal() {
                return false;
            }
        };
        for (Point2i center : centers) {
            Optional<List<Point2i>> path = pathFinder2d.findPath(mover, xCenterLast, yCenterLast, center.x, center.y);
            xCenterLast = center.x;
            yCenterLast = center.y;

//            if (path.isPresent()) {
            path.get().stream().forEach(it -> {
                CellType cellType = em.getCellTypeAt(it.x, it.y);
                CellType out = floor;
                if (cellType == grass || cellType == forest) {
                    out = grass;
                } else if (cellType == wall || cellType == door) {
                    out = door;
                }
                em.setCellTypeAt(it.x, it.y, out);
            });
//            }

//            fill(em, (e, x, y) -> false,
//                    (e, x, y) -> {
//                    }
//            );
        }


        printMap(em);
    }

    private void testEquality(ExampleGeneratedMap2d map1, ExampleGeneratedMap2d map2) {
        for (int y = 0; y < map1.getXSize(); y++) {
            for (int x = 0; x < map1.getXSize(); x++) {
                assert map1.getCellTypeAt(x, y) == map2.getCellTypeAt(x, y);
            }
        }
    }

    private void printMap(ExampleGeneratedMap2d exampleGeneratedMap2D) {
        for (int y = 0; y < exampleGeneratedMap2D.getXSize(); y++) {
            for (int x = 0; x < exampleGeneratedMap2D.getXSize(); x++) {

                System.out.print(((ExampleCellType) exampleGeneratedMap2D.getCellTypeAt(x, y)).getGlyph());
            }
            System.out.println();
        }
    }
}
