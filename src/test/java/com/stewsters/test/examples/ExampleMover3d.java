package com.stewsters.test.examples;

import com.stewsters.util.pathing.threeDimention.shared.Mover3d;
import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;

public class ExampleMover3d implements Mover3d {

    private ExampleMap3d exampleMap3d;

    public ExampleMover3d(ExampleMap3d exampleMap3d) {
        this.exampleMap3d = exampleMap3d;
    }

    @Override
    public boolean canTraverse(PathNode3d pathNode) {
        return !exampleMap3d.blocked(this, pathNode);
    }
}