package com.stewsters.util.spatial;

import java.util.*;

/**
 * This is a reimplementation of the C# 3d interval kd tree from:
 * <p/>
 * 3D Interval KD Tree implementation.
 * Author: Tommi S. E. Laukkanen
 * Project: http://www.bubblecloud.org
 * License: Apache 2.0 License
 *
 * @param <T> The type of object to be stored here
 */
public class IntervalKDTree3d<T> {

    private Node rootNode;
    private int divisionThreshold;

    private Map<Box, Node> boxNodeDictionary = new HashMap<Box, Node>();
    private Map<T, Box> valueBoxDictionary = new HashMap<T, Box>();

    public IntervalKDTree3d(double range, int divisionThreshold) {
        this.divisionThreshold = divisionThreshold;
        rootNode = new Node(this, null, 0, -range, -range, -range, range, range, range);
    }


    public void put(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, T value) {
        if (!valueBoxDictionary.containsKey(value)) {
            addBox(new Box(value, minX, minY, minZ, maxX, maxY, maxZ));
        } else {
            Box box = valueBoxDictionary.get(value);
            box.minX = minX;
            box.minY = minY;
            box.minZ = minZ;
            box.maxX = maxX;
            box.maxY = maxY;
            box.maxZ = maxZ;
            updateBox(box);
        }
    }

    public HashSet<T> getValues(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, HashSet<T> values) {
        rootNode.getValues(new Cube(minX, minY, minZ, maxX, maxY, maxZ), values);
        return values;
    }

    public List<T> getValues(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, List<T> values) {
        rootNode.getValues(new Cube(minX, minY, minZ, maxX, maxY, maxZ), values);
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
                        (box.isBelow(currentNode.depth, currentNode.divisionBoundary)
                                || box.isAbove(currentNode.depth, currentNode.divisionBoundary))
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

        private IntervalKDTree3d<T> tree;
        private List<Box> boxes;

        public Node(IntervalKDTree3d<T> tree, Node parent, int depth, double minX, double minY, double minZ,
                    double maxX, double maxY, double maxZ) {
            super(minX, minY, minZ, maxX, maxY, maxZ);
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
                lowChild = new Node(tree, this, depth + 1, minX, minY, minZ, divisionBoundary, maxY, maxZ);
                highChild = new Node(tree, this, depth + 1, divisionBoundary, minY, minZ, maxX, maxY, maxZ);
            } else if (dimension == 1) {
                divisionBoundary = (maxY + minY) / 2;
                lowChild = new Node(tree, this, depth + 1, minX, minY, minZ, maxX, divisionBoundary, maxZ);
                highChild = new Node(tree, this, depth + 1, minX, divisionBoundary, minZ, maxX, maxY, maxZ);
            } else {
                divisionBoundary = (maxZ + minZ) / 2;
                lowChild = new Node(tree, this, depth + 1, minX, minY, minZ, maxX, maxY, divisionBoundary);
                highChild = new Node(tree, this, depth + 1, minX, minY, divisionBoundary, maxX, maxY, maxZ);
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

        public Box(T value, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)

        {
            super(minX, minY, minZ, maxX, maxY, maxZ);
            this.value = value;
        }
    }


    /**
     * Axis aligned 3d cube implementation with math functions.
     */
    class Cube {

        public double minX;
        public double minY;
        public double minZ;
        public double maxX;
        public double maxY;
        public double maxZ;

        public Cube(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public boolean isBelow(int depth, double boundary) {
            int dimension = depth % 3;
            if (dimension == 0) {
                return maxX < boundary;
            } else if (dimension == 1) {
                return maxY < boundary;
            } else {
                return maxZ < boundary;
            }
        }

        public boolean isAbove(int depth, double boundary) {
            int dimension = depth % 3;
            if (dimension == 0) {
                return boundary <= minX;
            } else if (dimension == 1) {
                return boundary <= minY;
            } else {
                return boundary <= minZ;
            }
        }

        public boolean contains(Cube cube) {
            return minX <= cube.minX && cube.maxX < maxX &&
                    minY <= cube.minY && cube.maxY < maxY &&
                    minZ <= cube.minZ && cube.maxZ < maxZ;
        }

        public boolean intersects(Cube cube) {
            return (contains(cube.minX, cube.minY, cube.minZ) || contains(cube.maxX, cube.maxY, cube.maxZ)) ||
                    (cube.contains(minX, minY, minZ) || cube.contains(maxX, maxY, maxZ));
        }

        private boolean contains(double cx, double cy, double cz) {
            return minX <= cx && cx < maxX &&
                    minY <= cy && cy < maxY &&
                    minZ <= cz && cz < maxZ;
        }

    }
}

