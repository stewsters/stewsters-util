package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.pathing.threeDimention.pathfinder.AStarPathFinder3d;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarPathFinder2d;
import com.stewsters.util.pathing.twoDimention.pathfinder.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

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
        pathfinder2d = new AStarPathFinder2d(map2d, 1000, true);
        exampleMover2d = new ExampleMover2d(map2d);

        ExampleMap3d map3d = new ExampleMap3d(100, 100, 100, floor);
        pathfinder3d = new AStarPathFinder3d(map3d, 1000, true);
        exampleMover3d = new ExampleMover3d(map3d);

    }


    //======== benchmarks ========//
    @Benchmark
    public void timePathing2d() {
        FullPath2d fullPath2d = pathfinder2d.findPath(exampleMover2d, 0, 0, 99, 99);
        assert fullPath2d != null;
        assert fullPath2d.getLength() == 198;
    }

    @Benchmark
    public void timePathing3d() {
        FullPath3d fullPath3d = pathfinder3d.findPath(exampleMover3d, 0, 0, 0, 99, 99, 99);
        assert fullPath3d != null;
        assert fullPath3d.getLength() == 198;
    }
}
