package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.AStarHeuristic3d;
import com.stewsters.util.pathing.threeDimention.heuristic.ChebyshevHeuristic3d;
import com.stewsters.util.pathing.threeDimention.searcher.DjikstraSearcher3d;
import com.stewsters.util.pathing.threeDimention.searcher.Objective3d;
import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.twoDimention.heuristic.AStarHeuristic2d;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.searcher.DjikstraSearcher2d;
import com.stewsters.util.pathing.twoDimention.searcher.Objective2d;
import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.List;

@State(Scope.Thread)
public class SearchingBenchmark {

    DjikstraSearcher2d searcher2d;
    Objective2d objective2d;
    CanTraverse2d canTraverse2d;
    MovementCost2d movementCost2d;
    AStarHeuristic2d aStarHeuristic2d;

    DjikstraSearcher3d searcher3d;
    Objective3d objective3d;
    CanTraverse3d canTraverse3d;
    MovementCost3d movementCost3d;
    AStarHeuristic3d aStarHeuristic3d;

    @Setup
    public void prepare() {
        ExampleCellType floor = new ExampleCellType('.', false);

        ExampleMap2d map2d = new ExampleMap2d(100, 100, floor);
        searcher2d = new DjikstraSearcher2d(map2d, 1000);
        objective2d = (PathNode2d current) -> (current.x == 99 && current.y == 99);
        canTraverse2d = ((sx, sy, tx, ty) -> !map2d.isBlocked(tx, ty));
        movementCost2d = (int sx, int sy, int tx, int ty) -> 1.0f;
        aStarHeuristic2d = new ChebyshevHeuristic2d();


        ExampleMap3d map3d = new ExampleMap3d(10, 10, 10, floor);
        searcher3d = new DjikstraSearcher3d(map3d, 1000);
        objective3d = (PathNode3d current) -> (current.x == 9 && current.y == 9 && current.z == 9);
        canTraverse3d = (int sx, int sy, int sz, int tx, int ty, int tz) -> !map3d.isBlocked(tx, ty, tz);
        movementCost3d = (int sx, int sy, int sz, int tx, int ty, int tz) -> 1.0f;
        aStarHeuristic3d = new ChebyshevHeuristic3d();
    }

    @Benchmark
    public void timeSearching2d() {
        List<Point2i> fullPath2d = searcher2d.search(
                objective2d,
                canTraverse2d,
                movementCost2d,
                true,
                0, 0).get();
        assert fullPath2d != null;
        assert fullPath2d.size() == 198;
    }

    @Benchmark
    public void timeSearching3d() {
        List<Point3i> fullPath3d = searcher3d.search(
                objective3d,
                canTraverse3d,
                movementCost3d,
                true,
                0, 0, 0).get();
        assert fullPath3d != null;
        assert fullPath3d.size() == 198;
    }
}
