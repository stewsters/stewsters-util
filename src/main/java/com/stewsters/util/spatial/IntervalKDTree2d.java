package com.stewsters.util.spatial;

import java.util.*;

/**
 * This is 2d java reimplementation of the C# 3d interval kd tree from:
 *
 * 3D Interval KD Tree implementation.
 * Author: Tommi S. E. Laukkanen
 * Project: http://www.bubblecloud.org
 * License: Apache 2.0 License
 *
 * @param <T> The type of object to be stored here
 */
public class IntervalKDTree2d<T> {

    private Node rootNode;
    private int divisionThreshold;

    private Map<Box, Node> boxNodeDictionary = new HashMap<Box, Node>();
    private Map<T, Box> valueBoxDictionary = new HashMap<T, Box>();

    public IntervalKDTree2d(double range, int divisionThreshold) {
        this.divisionThreshold = divisionThreshold;
        rootNode = new Node(this, null, 0, -range, -range, range, range);
    }


    public void put(double minX, double minY, double maxX, double maxY, T value) {
        if (!valueBoxDictionary.containsKey(value)) {
            addBox(new Box(value, minX, minY, maxX, maxY));
        } else {
            Box box = valueBoxDictionary.get(value);
            box.minX = minX;
            box.minY = minY;
            box.maxX = maxX;
            box.maxY = maxY;
            updateBox(box);
        }
    }

    public HashSet<T> getValues(double minX, double minY, double maxX, double maxY, HashSet<T> values) {
        rootNode.getValues(new Cube(minX, minY, maxX, maxY), values);
        return values;
    }

    public List<T> getValues(double minX, double minY, double maxX, double maxY, List<T> values) {
        rootNode.getValues(new Cube(minX, minY, maxX, maxY), values);
        return values;
    }

    public void remove(T value) {
        if (valueBoxDictionary.containsKey(value)) {
            Box box = valueBoxDictionary.get(value);
            removeBox(box);
            boxNodeDictionary.remove(box);
            valueBoxDictionary.remove(value);
        }
    }

    private void addBox(Box box) {
        rootNode.addBox(box);
        valueBoxDictionary.put((T) box.value, box);
    }

    private void updateBox(Box box) {
        Node currentNode = boxNodeDictionary.get(box);

        if (
                (currentNode.hasChildren &&
                        (box.isBelow(currentNode.depth, currentNode.divisionBoundary) ||
                                box.isAbove(currentNode.depth, currentNode.divisionBoundary))
                )
                        || !currentNode.contains(box)
                ) {
            // If box location has changed so that it needs to placed to different node then remove / add.
            currentNode.removeBox(box);
            rootNode.addBox(box);
        }
    }

    private void removeBox(Box box) {
        boxNodeDictionary.get(box).removeBox(box);
        valueBoxDictionary.remove(box.value);
    }


    /**
     * Node is extend axis aligned 3d cube which is the node implementation of IntervalKDTree.
     */
    private class Node extends Cube {
        public int depth;
        public double divisionBoundary;

        public boolean hasChildren = false;

        public Node parent;
        public Node lowChild;
        public Node highChild;

        private IntervalKDTree2d<T> tree;
        private List<Box> boxes;

        public Node(IntervalKDTree2d<T> tree, Node parent, int depth, double minX, double minY,
                    double maxX, double maxY) {
            super(minX, minY, maxX, maxY);
            this.tree = tree;
            this.parent = parent;
            this.depth = depth;
            boxes = new LinkedList<Box>();
        }

        public void addBox(Box box) {
            if (hasChildren) {
                if (box.isBelow(depth, divisionBoundary)) {
                    lowChild.addBox(box);
                    return;
                }
                if (box.isAbove(depth, divisionBoundary)) {
                    highChild.addBox(box);
                    return;
                }
            }

            boxes.add(box);

            tree.boxNodeDictionary.put(box, this);

            // Divide to children if threshold has been exceeded
            if (!hasChildren && boxes.size() > tree.divisionThreshold) {
                divide();
            }

            return;
        }

        public void removeBox(Box box) {
            boxes.remove(box);

            // Collapse parent node if total amount of values in parent and childen is less than maximum values per node.
            if (parent != null && !parent.lowChild.hasChildren && !parent.highChild.hasChildren &&
                    parent.boxes.size() + parent.lowChild.boxes.size() + parent.highChild.boxes.size() < tree.divisionThreshold) {
                parent.collapse();
            }
        }

        public void getValues(Cube cube, HashSet<T> values) {
            for (Box box : boxes) {
                if (cube.intersects(box)) {
                    values.add((T) box.value);
                }
            }

            if (hasChildren) {
                if (cube.isBelow(depth, divisionBoundary)) {
                    lowChild.getValues(cube, values);
                } else if (cube.isAbove(depth, divisionBoundary)) {
                    highChild.getValues(cube, values);
                } else {
                    lowChild.getValues(cube, values);
                    highChild.getValues(cube, values);
                }
            }
        }

        public void getValues(Cube cube, List<T> values) {
            for (Box box : boxes) {
                if (cube.intersects(box)) {
                    values.add((T) box.value);
                }
            }

            if (hasChildren) {
                if (cube.isBelow(depth, divisionBoundary)) {
                    lowChild.getValues(cube, values);
                } else if (cube.isAbove(depth, divisionBoundary)) {
                    highChild.getValues(cube, values);
                } else {
                    lowChild.getValues(cube, values);
                    highChild.getValues(cube, values);
                }
            }
        }

        private void divide() {
            if (hasChildren == true) {
                throw new RuntimeException("Already has children.");
            }

            hasChildren = true;

            int dimension = depth % 3;
            if (dimension == 0) {
                divisionBoundary = (maxX + minX) / 2;
                lowChild = new Node(tree, this, depth + 1, minX, minY, divisionBoundary, maxY);
                highChild = new Node(tree, this, depth + 1, divisionBoundary, minY, maxX, maxY);
            } else {
                divisionBoundary = (maxY + minY) / 2;
                lowChild = new Node(tree, this, depth + 1, minX, minY, maxX, divisionBoundary);
                highChild = new Node(tree, this, depth + 1, minX, divisionBoundary, maxX, maxY);
            }

            List<Box> oldBoxList = boxes;
            boxes = new LinkedList<Box>();
            for (Box cube : oldBoxList) {
                addBox(cube);
            }

        }

        private void collapse() {
            for (Box box : lowChild.boxes) {
                boxes.add(box);
                tree.boxNodeDictionary.put(box, this);
            }
            for (Box box : highChild.boxes) {
                boxes.add(box);
                tree.boxNodeDictionary.put(box, this);
            }
            lowChild = null;
            highChild = null;
            hasChildren = false;

            if (parent != null && !parent.lowChild.hasChildren && !parent.highChild.hasChildren &&
                    parent.boxes.size() + parent.lowChild.boxes.size() + parent.highChild.boxes.size() < tree.divisionThreshold) {
                parent.collapse();
            }
        }

    }

    /**
     * Box is axis aligned 3d cube which can hold value. Acts as capsule in IntervalKDTree data structure.
     *
     * @param <T> The thing being stored
     */
    class Box<T> extends Cube {
        public T value;

        public Box(T value, double minX, double minY, double maxX, double maxY)

        {
            super(minX, minY, maxX, maxY);
            this.value = value;
        }
    }


    /**
     * Axis aligned 3d cube implementation with math functions.
     */
    class Cube {

        public double minX;
        public double minY;
        public double maxX;
        public double maxY;


        public Cube(double minX, double minY, double maxX, double maxY) {
            this.minX = minX;
            this.minY = minY;

            this.maxX = maxX;
            this.maxY = maxY;

        }

        public boolean isBelow(int depth, double boundary) {
            int dimension = depth % 2;
            if (dimension == 0) {
                return maxX < boundary;
            } else {
                return maxY < boundary;
            }
        }

        public boolean isAbove(int depth, double boundary) {
            int dimension = depth % 2;
            if (dimension == 0) {
                return boundary <= minX;
            } else {
                return boundary <= minY;
            }
        }

        public boolean contains(Cube cube) {
            return minX <= cube.minX && cube.maxX < maxX &&
                    minY <= cube.minY && cube.maxY < maxY;
        }

        public boolean intersects(Cube cube) {
            return (contains(cube.minX, cube.minY) || contains(cube.maxX, cube.maxY)) ||
                    (cube.contains(minX, minY) || cube.contains(maxX, maxY));
        }

        private boolean contains(double cx, double cy) {
            return minX <= cx && cx < maxX &&
                    minY <= cy && cy < maxY;
        }

    }
}

