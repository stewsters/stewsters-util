package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;

public class ExampleMover2d implements Mover2d {

    private ExampleMap2d exampleMap2d;

    public ExampleMover2d(ExampleMap2d exampleMap2d) {
        this.exampleMap2d = exampleMap2d;
    }

    @Override
    public boolean canTraverse(PathNode2d pathNode) {
        return !exampleMap2d.blocked(this, pathNode);
    }
}