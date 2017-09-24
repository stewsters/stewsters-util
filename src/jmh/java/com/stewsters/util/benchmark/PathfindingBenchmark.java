package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.AStarHeuristic3d;
import com.stewsters.util.pathing.threeDimention.heuristic.ChebyshevHeuristic3d;
import com.stewsters.util.pathing.threeDimention.pathfinder.AStarPathFinder3d;
import com.stewsters.util.pathing.threeDimention.shared.CanOccupy3d;
import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import com.stewsters.util.pathing.twoDimention.shared.CanOccupy2d;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.List;

@State(Scope.Thread)
public class PathfindingBenchmark {

    AStarPathFinder2d pathfinder2d;
    CanTraverse2d canTraverse2d;
    CanOccupy2d canOccupy2d;
    MovementCost2d movementCost2d;
    AStarHeuristic2d aStarHeuristic2d;


    AStarPathFinder3d pathfinder3d;
    CanTraverse3d canTraverse3d;
    CanOccupy3d canOccupy3d;
    MovementCost3d movementCost3d;
    AStarHeuristic3d aStarHeuristic3d;

    @Setup
    public void prepare() {

        ExampleCellType floor = new ExampleCellType('.', false);
        ExampleCellType wall = new ExampleCellType('#', true);

        ExampleMap2d map2d = new ExampleMap2d(100, 100, floor);
        pathfinder2d = new AStarPathFinder2d(map2d, 1000);
        canTraverse2d = ((sx, sy, tx, ty) -> !map2d.isBlocked(tx, ty));
        canOccupy2d = (tx, ty) -> !map2d.isBlocked(tx, ty);
        movementCost2d = (int sx, int sy, int tx, int ty) -> 1.0f;
        aStarHeuristic2d = new ChebyshevHeuristic2d();

        ExampleMap3d map3d = new ExampleMap3d(100, 100, 100, floor);
        pathfinder3d = new AStarPathFinder3d(map3d, 1000);
        canTraverse3d = (int sx, int sy, int sz, int tx, int ty, int tz) -> !map3d.isBlocked(tx, ty, tz);
        canOccupy3d = (tx, ty, tz) -> !map2d.isBlocked(tx, ty);
        movementCost3d = (int sx, int sy, int sz, int tx, int ty, int tz) -> 1.0f;
        aStarHeuristic3d = new ChebyshevHeuristic3d();
    }


    //======== benchmarks ========//
    @Benchmark
    public void timePathing2d() {
        List<Point2i> fullPath2d = pathfinder2d.findPath(
                canTraverse2d,
                canOccupy2d,
                movementCost2d,
                aStarHeuristic2d,
                true,
                0, 0, 99, 99).get();
        assert fullPath2d != null;
        assert fullPath2d.size() == 198;
    }

    @Benchmark
    public void timePathing3d() {
        List<Point3i> fullPath3d = pathfinder3d.findPath(
                canTraverse3d,
                canOccupy3d,
                movementCost3d,
                aStarHeuristic3d,
                true,
                0, 0, 0, 99, 99, 99).get();
        assert fullPath3d != null;
        assert fullPath3d.size() == 198;
    }
}
