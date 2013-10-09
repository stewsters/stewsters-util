package com.stewsters.util.pathing.threeDimention.shared;

/**
 * A single PathNode in the search graph
 */
public class PathNode3d implements Comparable {
    /**
     * The x coordinate of the node
     */
    public int x;
    /**
     * The y coordinate of the node
     */
    public int y;
    /**
     * The z coordinate of the node
     */
    public int z;
    /**
     * The path cost for this node
     */
    public float cost;
    /**
     * The parent of this node, how we reached it in the search
     */
    public PathNode3d parent;
    /**
     * The heuristic cost of this node
     */
    public float heuristic;
    /**
     * The search depth of this node
     */
    public int depth;

    /**
     * Create a new node
     *
     * @param x The x coordinate of the node
     * @param y The y coordinate of the node
     */
    public PathNode3d(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
    public int compareTo(Object other) {
        PathNode3d o = (PathNode3d) other;

        float f = heuristic + cost;
        float of = o.heuristic + o.cost;

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
    }
}