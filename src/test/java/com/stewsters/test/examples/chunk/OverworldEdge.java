package com.stewsters.test.examples.chunk;

public class OverworldEdge {

    public final OverworldPathNode left;
    public final OverworldPathNode right;
    public final int cost;

    public OverworldEdge(OverworldPathNode leftNode, OverworldPathNode rightNode, int cost) {
        left = leftNode;
        right = rightNode;
        this.cost = cost;

        left.edges.add(this);
        right.edges.add(this);
    }
}
