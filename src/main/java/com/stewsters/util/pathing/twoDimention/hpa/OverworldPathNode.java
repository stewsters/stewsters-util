package com.stewsters.util.pathing.twoDimention.hpa;

import java.util.ArrayList;

public class OverworldPathNode implements Comparable<OverworldPathNode> {

    public final Chunk2d chunk;
    public final ArrayList<OverworldEdge> edges;
    private final int x;
    private final int y;
    public float cost;
    public OverworldPathNode parent;
    public float heuristic;
    public int depth;

    public OverworldPathNode(Chunk2d chunk, int x, int y) {
        this.chunk = chunk;
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
    }

    public int getPreciseX() {
        return x;
    }

    public int getPreciseY() {
        return y;
    }

    public int getGlobalX() {
        return x + chunk.getXOffset() * chunk.getXSize();
    }

    public int getGlobalY() {
        return y + chunk.getYOffset() * chunk.getYSize();
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

    @Override
    public String toString() {
        return chunk.toString() + " _ " + x + ", " + y;
    }
}
