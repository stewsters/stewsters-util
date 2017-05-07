package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.pathing.threeDimention.heuristic.ChebyshevHeuristic3d;
import com.stewsters.util.pathing.threeDimention.searcher.DjikstraSearcher3d;
import com.stewsters.util.pathing.threeDimention.searcher.Objective3d;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.twoDimention.heuristic.ChebyshevHeuristic2d;
import com.stewsters.util.pathing.twoDimention.searcher.DjikstraSearcher2d;
import com.stewsters.util.pathing.twoDimention.searcher.Objective2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class SearchingBenchmark {

    DjikstraSearcher2d searcher2d;
    ExampleMover2d exampleMover2d;
    Objective2d objective2d;

    DjikstraSearcher3d searcher3d;
    ExampleMover3d exampleMover3d;
    Objective3d objective3d;

    @Setup
    public void prepare() {
        ExampleCellType floor = new ExampleCellType('.', false);

        ExampleMap2d map2d = new ExampleMap2d(100, 100, floor);
        searcher2d = new DjikstraSearcher2d(map2d, 1000);
        exampleMover2d = new ExampleMover2d(map2d, new ChebyshevHeuristic2d(), true);
        objective2d = new Objective2d() {
            @Override
            public boolean satisfiedBy(PathNode2d current) {
                return (current.x == 99 && current.y == 99);
            }
        };


        ExampleMap3d map3d = new ExampleMap3d(10, 10, 10, floor);
        searcher3d = new DjikstraSearcher3d(map3d, 1000, true);
        exampleMover3d = new ExampleMover3d(map3d, new ChebyshevHeuristic3d(), true);
        objective3d = new Objective3d() {
            @Override
            public boolean satisfiedBy(PathNode3d current) {
                return (current.x == 9 && current.y == 9 && current.z == 9);
            }
        };
    }

    @Benchmark
    public void timeSearching2d() {
        FullPath2d fullPath2d = searcher2d.search(exampleMover2d, 0, 0, objective2d);
        assert fullPath2d != null;
        assert fullPath2d.getLength() == 198;
    }

    @Benchmark
    public void timeSearching3d() {
        FullPath3d fullPath3d = searcher3d.search(exampleMover3d, 0, 0, 0, objective3d);
        assert fullPath3d != null;
        assert fullPath3d.getLength() == 198;
    }
}
