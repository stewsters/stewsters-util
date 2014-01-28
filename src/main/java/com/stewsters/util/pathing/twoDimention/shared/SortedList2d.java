package com.stewsters.util.pathing.twoDimention.shared;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple sorted list
 *
 * @author kevin
 */
public class SortedList2d {
    /**
     * The list of elements
     */
    private ArrayList<PathNode2d> list = new ArrayList<PathNode2d>();

    /**
     * Retrieve the first element from the list
     *
     * @return The first element from the list
     */
    public PathNode2d first() {
        return list.get(0);
    }

    /**
     * Empty the list
     */
    public void clear() {
        list.clear();
    }

    /**
     * Add an element to the list - causes sorting
     *
     * @param o The element to add
     */
    public void add(PathNode2d o) {
        list.add(o);
        Collections.sort(list);
    }

    /**
     * Remove an element from the list
     *
     * @param o The element to remove
     */
    public void remove(PathNode2d o) {
        list.remove(o);
    }

    /**
     * Get the number of elements in the list
     *
     * @return The number of element in the list
     */
    public int size() {
        return list.size();
    }

    /**
     * Check if an element is in the list
     *
     * @param o The element to search for
     * @return True if the element is in the list
     */
    public boolean contains(PathNode2d o) {
        return list.contains(o);
    }
}
