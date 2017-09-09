package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.math.Point3i;
import com.stewsters.util.pathing.threeDimention.heuristic.ChebyshevHeuristic3d;
import com.stewsters.util.pathing.threeDimention.pathfinder.AStarPathFinder3d;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.List;

@State(Scope.Thread)
public class PathfindingBenchmark {

    AStarPathFinder2d pathfinder2d;
    ExampleMover2d exampleMover2d;

    AStarPathFinder3d pathfinder3d;
    ExampleMover3d exampleMover3d;

    @Setup
    public void prepare() {

        ExampleCellType floor = new ExampleCellType('.', false);
        ExampleCellType wall = new ExampleCellType('#', true);

        ExampleMap2d map2d = new ExampleMap2d(100, 100, floor);
        pathfinder2d = new AStarPathFinder2d(map2d, 1000);
        exampleMover2d = new ExampleMover2d(map2d, new ChebyshevHeuristic2d(), true);

        ExampleMap3d map3d = new ExampleMap3d(100, 100, 100, floor);
        pathfinder3d = new AStarPathFinder3d(map3d, 1000, true);
        exampleMover3d = new ExampleMover3d(map3d, new ChebyshevHeuristic3d(), true);

    }


    //======== benchmarks ========//
    @Benchmark
    public void timePathing2d() {
        List<Point2i> fullPath2d = pathfinder2d.findPath(exampleMover2d, 0, 0, 99, 99).get();
        assert fullPath2d != null;
        assert fullPath2d.size() == 198;
    }

    @Benchmark
    public void timePathing3d() {
        List<Point3i> fullPath3d = pathfinder3d.findPath(exampleMover3d, 0, 0, 0, 99, 99, 99).get();
        assert fullPath3d != null;
        assert fullPath3d.size() == 198;
    }
}
