package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.util.pathing.twoDimention.searcher.DjikstraSearcher2d;
import com.stewsters.util.pathing.twoDimention.searcher.Objective2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class Searching2dBenchmark {

    DjikstraSearcher2d searcher2d;
    ExampleMover2d exampleMover2d;
    Objective2d objective2d;

    @Setup
    public void prepare() {
        ExampleCellType floor = new ExampleCellType('.', false);

        ExampleMap2d map2d = new ExampleMap2d(100, 100, floor);
        searcher2d = new DjikstraSearcher2d(map2d, 1000, false);
        exampleMover2d = new ExampleMover2d(map2d);
        objective2d = new Objective2d() {
            @Override
            public boolean satisfiedBy(PathNode2d current) {
                return (current.x == 98 && current.y == 98);
            }
        };

    }

    @Benchmark
    public void timeSearching2d() {
        FullPath2d fullPath2d = searcher2d.search(exampleMover2d, 1, 1, objective2d);
        assert fullPath2d != null;
    }
}
