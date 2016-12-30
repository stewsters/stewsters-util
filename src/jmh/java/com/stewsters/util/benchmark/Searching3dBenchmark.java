package com.stewsters.util.benchmark;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.test.examples.ExampleMover3d;
import com.stewsters.util.pathing.threeDimention.searcher.DjikstraSearcher3d;
import com.stewsters.util.pathing.threeDimention.searcher.Objective3d;
import com.stewsters.util.pathing.threeDimention.shared.FullPath3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class Searching3dBenchmark {

    DjikstraSearcher3d searcher3d;
    ExampleMover3d exampleMover3d;
    Objective3d objective3d;

    @Setup
    public void prepare() {
        ExampleCellType floor = new ExampleCellType('.', false);

        ExampleMap3d map3d = new ExampleMap3d(100, 100, 3, floor);
        searcher3d = new DjikstraSearcher3d(map3d, 1000, false);
        exampleMover3d = new ExampleMover3d(map3d);
        objective3d = new Objective3d() {
            @Override
            public boolean satisfiedBy(PathNode3d current) {
                return (current.x == 98 && current.y == 98 && current.z == 98);
            }
        };
    }

    @Benchmark
    public void timeSearching3d() {
        FullPath3d fullPath3d = searcher3d.search(exampleMover3d, 1, 1, 1, objective3d);
        assert fullPath3d != null;
    }
}
