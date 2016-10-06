package com.stewsters.util.pathing.twoDimention.shared;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A path determined by some path finding algorithm. A series of steps from
 * the starting location to the target location. This includes a step for the
 * initial location.
 *
 * @author Kevin Glass
 */
public class FullPath2d {
    /**
     * The list of steps building up this path
     */
    private ArrayList<Step> steps = new ArrayList<>();

    /**
     * Create an empty path
     */
    public FullPath2d() {

    }

    /**
     * Get the length of the path, i.e. the number of steps
     *
     * @return The number of steps in this path
     */
    public int getLength() {
        return steps.size();
    }

    /**
     * Get the step at a given index in the path
     *
     * @param index The index of the step to retrieve. Note this should
     *              be gte 0 and lt getLength();
     * @return The step information, the position on the map.
     */
    public Step getStep(int index) {
        return steps.get(index);
    }

    /**
     * Get the x coordinate for the step at the given index
     *
     * @param index The index of the step whose x coordinate should be retrieved
     * @return The x coordinate at the step
     */
    public int getX(int index) {
        return getStep(index).x;
    }

    /**
     * Get the y coordinate for the step at the given index
     *
     * @param index The index of the step whose y coordinate should be retrieved
     * @return The y coordinate at the step
     */
    public int getY(int index) {
        return getStep(index).y;
    }

    /**
     * Append a step to the path.
     *
     * @param x The x coordinate of the new step
     * @param y The y coordinate of the new step
     */
    public void appendStep(int x, int y) {
        steps.add(new Step(x, y));
    }

    /**
     * Prepend a step to the path.
     *
     * @param x The x coordinate of the new step
     * @param y The y coordinate of the new step
     */
    public void prependStep(int x, int y) {
        steps.add(0, new Step(x, y));
    }

    /**
     * Check if this path contains the given step
     *
     * @param x The x coordinate of the step to check for
     * @param y The y coordinate of the step to check for
     * @return True if the path contains the given step
     */
    public boolean contains(int x, int y) {
        return steps.contains(new Step(x, y));
    }

    public void reverse() {
        Collections.reverse(steps);
    }


    /**
     * A single step within the path
     *
     * @author Kevin Glass
     */
    public class Step {
        private int x;
        private int y;

        public Step(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }


        /**
         * @see Object#hashCode()
         */
        public int hashCode() {
            int hash = 17;
            hash = ((hash + x) << 5) - (hash + x);
            hash = ((hash + y) << 5) - (hash + y);
            return hash;
        }

        /**
         * @param other the object to compare to
         * @return if it is equal
         */
        public boolean equals(Step other) {

            return (other.x == x) && (other.y == y);

        }
    }
}
