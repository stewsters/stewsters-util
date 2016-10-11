package com.stewsters.test.examples.chunk;

import java.util.ArrayList;

public class OverworldPathNode implements Comparable<OverworldPathNode> {

    public final Chunk chunk;
    public final int x;
    public final int y;

    public final ArrayList<OverworldEdge> edges;


    public float cost;
    public OverworldPathNode parent;
    public float heuristic;
    public int depth;

    public OverworldPathNode(Chunk chunk, int x, int y) {
        this.chunk = chunk;
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
    }

    public int getGlobalX() {
        return x + chunk.xOffset;
    }

    public int getGlobalY() {
        return y + chunk.yOffset;
    }

    public int setParent(OverworldPathNode parent) {
        depth = parent.depth + 1;
        this.parent = parent;

        return depth;
    }

    @Override
    public int compareTo(OverworldPathNode other) {

        float f = heuristic + cost;
        float of = other.heuristic + other.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }
}
