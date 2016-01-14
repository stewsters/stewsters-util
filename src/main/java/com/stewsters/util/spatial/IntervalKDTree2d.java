package com.stewsters.util.spatial;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * This is 2d java reimplementation of the C# 3d interval kd tree from
 * </p>
 * <p>
 * 3D Interval KD Tree implementation.
 * Author: Tommi S. E. Laukkanen
 * Project: http://www.bubblecloud.org
 * License: Apache 2.0 License
 * </p>
 *
 * @param <T> The type of object to be stored here
 */
public class IntervalKDTree2d<T> {

    private Node rootNode;
    private int divisionThreshold;

    private Map<Box<T>, Node> boxNodeDictionary = new HashMap<>();
    private Map<T, Box<T>> valueBoxDictionary = new HashMap<T, Box<T>>();

    public IntervalKDTree2d(double range, int divisionThreshold) {
        this.divisionThreshold = divisionThreshold;
        rootNode = new Node(this, null, 0, -range, -range, range, range);
    }


    public void put(double minX, double minY, double maxX, double maxY, T value) {
        if (!valueBoxDictionary.containsKey(value)) {
            addBox(new Box<T>(value, minX, minY, maxX, maxY));
        } else {
            Box<T> box = valueBoxDictionary.get(value);
            box.minX = minX;
            box.minY = minY;
            box.maxX = maxX;
            box.maxY = maxY;
            updateBox(box);
        }
    }

    public HashSet<T> getValues(double minX, double minY, double maxX, double maxY, HashSet<T> values) {
        rootNode.getValues(new Square(minX, minY, maxX, maxY), values);
        return values;
    }

    public List<T> getValues(double minX, double minY, double maxX, double maxY, List<T> values) {
        rootNode.getValues(new Square(minX, minY, maxX, maxY), values);
        return values;
    }

    public void remove(T value) {
        if (valueBoxDictionary.containsKey(value)) {
            Box<T> box = valueBoxDictionary.get(value);
            removeBox(box);
            boxNodeDictionary.remove(box);
            valueBoxDictionary.remove(value);
        }
    }

    private void addBox(Box<T> box) {
        rootNode.addBox(box);
        valueBoxDictionary.put(box.value, box);
    }

    private void updateBox(Box<T> box) {
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

    private void removeBox(Box<T> box) {
        boxNodeDictionary.get(box).removeBox(box);
        valueBoxDictionary.remove(box.value);
    }


    /**
     * Node is extend axis aligned 2d cube which is the node implementation of IntervalKDTree.
     */
    private class Node extends Square {
        public int depth;
        public double divisionBoundary;

        public boolean hasChildren = false;

        public Node parent;
        public Node lowChild;
        public Node highChild;

        private IntervalKDTree2d<T> tree;
        private List<Box<T>> boxes;

        public Node(IntervalKDTree2d<T> tree, Node parent, int depth, double minX, double minY,
                    double maxX, double maxY) {
            super(minX, minY, maxX, maxY);
            this.tree = tree;
            this.parent = parent;
            this.depth = depth;
            boxes = new LinkedList<Box<T>>();
        }

        public void addBox(Box<T> box) {
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

        }

        public void removeBox(Box<T> box) {
            boxes.remove(box);

            // Collapse parent node if total amount of values in parent and childen is less than maximum values per node.
            if (parent != null && !parent.lowChild.hasChildren && !parent.highChild.hasChildren &&
                    parent.boxes.size() + parent.lowChild.boxes.size() + parent.highChild.boxes.size() < tree.divisionThreshold) {
                parent.collapse();
            }
        }

        public void getValues(Square square, HashSet<T> values) {
            for (Box<T> box : boxes) {
                if (square.intersects(box)) {
                    values.add(box.value);
                }
            }

            if (hasChildren) {
                if (square.isBelow(depth, divisionBoundary)) {
                    lowChild.getValues(square, values);
                } else if (square.isAbove(depth, divisionBoundary)) {
                    highChild.getValues(square, values);
                } else {
                    lowChild.getValues(square, values);
                    highChild.getValues(square, values);
                }
            }
        }

        public void getValues(Square square, List<T> values) {
            for (Box<T> box : boxes) {
                if (square.intersects(box)) {
                    values.add(box.value);
                }
            }

            if (hasChildren) {
                if (square.isBelow(depth, divisionBoundary)) {
                    lowChild.getValues(square, values);
                } else if (square.isAbove(depth, divisionBoundary)) {
                    highChild.getValues(square, values);
                } else {
                    lowChild.getValues(square, values);
                    highChild.getValues(square, values);
                }
            }
        }

        private void divide() {
            if (hasChildren) {
                throw new RuntimeException("Already has children.");
            }

            hasChildren = true;

            int dimension = depth % 2;
            if (dimension == 0) {
                divisionBoundary = (maxX + minX) / 2;
                lowChild = new Node(tree, this, depth + 1, minX, minY, divisionBoundary, maxY);
                highChild = new Node(tree, this, depth + 1, divisionBoundary, minY, maxX, maxY);
            } else {
                divisionBoundary = (maxY + minY) / 2;
                lowChild = new Node(tree, this, depth + 1, minX, minY, maxX, divisionBoundary);
                highChild = new Node(tree, this, depth + 1, minX, divisionBoundary, maxX, maxY);
            }

            List<Box<T>> oldBoxList = boxes;
            boxes = new LinkedList<Box<T>>();
            for (Box<T> cube : oldBoxList) {
                addBox(cube);
            }

        }

        private void collapse() {
            for (Box<T> box : lowChild.boxes) {
                boxes.add(box);
                tree.boxNodeDictionary.put(box, this);
            }
            for (Box<T> box : highChild.boxes) {
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
     * Box is axis aligned 2d square which can hold value. Acts as capsule in IntervalKDTree data structure.
     *
     * @param <K> The thing being stored
     */
    class Box<K> extends Square {
        public K value;

        public Box(K value, double minX, double minY, double maxX, double maxY)

        {
            super(minX, minY, maxX, maxY);
            this.value = value;
        }
    }


    /**
     * Axis aligned 2d square implementation with math functions.
     */
    class Square {

        public double minX;
        public double minY;
        public double maxX;
        public double maxY;


        public Square(double minX, double minY, double maxX, double maxY) {
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

        public boolean contains(Square square) {
            return minX <= square.minX && square.maxX < maxX &&
                    minY <= square.minY && square.maxY < maxY;
        }

        public boolean intersects(Square square) {
            return (contains(square.minX, square.minY) || contains(square.maxX, square.maxY)) ||
                    (square.contains(minX, minY) || square.contains(maxX, maxY));
        }

        private boolean contains(double cx, double cy) {
            return minX <= cx && cx < maxX &&
                    minY <= cy && cy < maxY;
        }

    }
}

