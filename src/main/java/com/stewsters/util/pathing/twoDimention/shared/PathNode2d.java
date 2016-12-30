package com.stewsters.util.pathing.twoDimention.shared;

/**
 * A single PathNode in the search graph
 */
public class PathNode2d implements Comparable<PathNode2d> {

    public int x;
    public int y;

    public float cost;
    public PathNode2d parent;
    public float heuristic;
    public int depth;

    public boolean closed;

    public PathNode2d(int x, int y) {
        this.x = x;
        this.y = y;
        this.closed = false;
    }

    /**
     * Set the parent of this node
     *
     * @param parent The parent PathNode which lead us to this node
     * @return The depth we have no reached in searching
     */
    public int setParent(PathNode2d parent) {
        depth = parent.depth + 1;
        this.parent = parent;

        return depth;
    }

    @Override
    public int compareTo(PathNode2d other) {

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
