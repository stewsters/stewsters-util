package com.stewsters.util.pathing.threeDimention.shared;

/**
 * A single PathNode in the search graph
 */
public class PathNode3d implements Comparable<PathNode3d> {

    public int x;
    public int y;
    public int z;

    public float cost;
    public PathNode3d parent;
    public float heuristic;
    public int depth;

    public boolean closed;

    /**
     * Create a new node
     *
     * @param x The x coordinate of the node
     * @param y The y coordinate of the node
     * @param z The z coordinate of the node
     */
    public PathNode3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.closed = false;
    }

    /**
     * Set the parent of this node
     *
     * @param parent The parent PathNode which lead us to this node
     * @return The depth we have no reached in searching
     */
    public int setParent(PathNode3d parent) {
        depth = parent.depth + 1;
        this.parent = parent;

        return depth;
    }

    /**
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(PathNode3d other) {

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
