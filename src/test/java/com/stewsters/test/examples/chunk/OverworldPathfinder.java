package com.stewsters.test.examples.chunk;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

public class OverworldPathfinder {

    public ArrayList<Point2i> getPath(OverworldExample overworld, int globalStartingX, int globalStartingY,
                                      int globalDestinationX, int globalDestinationY, Mover2dV2 mover2d, float maxSearchCost) {

        AStarHeuristic2d heuristic = mover2d.getHeuristic();
//        boolean allowDiag = mover2d.getDiagonal();

        if (!mover2d.canOccupy(globalDestinationX, globalDestinationY))
            return null;

        int preciseStartingX = globalStartingX % Chunk.xSize;
        int preciseStartingY = globalStartingY % Chunk.ySize;

        int preciseDestinationX = globalDestinationX % Chunk.xSize;
        int preciseDestinationY = globalDestinationY % Chunk.ySize;

        Chunk sourceChunk = overworld.getChunk(globalStartingX, globalStartingY);
        int sourceRegionId = sourceChunk.regionIds[preciseStartingX][preciseStartingY];

        Chunk destinationChunk = overworld.getChunk(globalDestinationX, globalDestinationY);
        int destinationRegionId = destinationChunk.regionIds[preciseDestinationX][preciseDestinationY];

        OverworldPathNode startingNode = new OverworldPathNode(sourceChunk, preciseStartingX, preciseStartingY);
        OverworldPathNode destinationNode = new OverworldPathNode(destinationChunk, preciseDestinationX, preciseDestinationY);

        // Add edges
        for (OverworldPathNode overworldPathNode : sourceChunk.overworldPathNodes) {
            if (sourceChunk.regionIds[overworldPathNode.getPreciseX()][overworldPathNode.getPreciseY()] == sourceRegionId) {
                new OverworldEdge(startingNode, overworldPathNode, 1);
            }
        }
        for (OverworldPathNode overworldPathNode : destinationChunk.overworldPathNodes) {
            if (sourceChunk.regionIds[overworldPathNode.getPreciseX()][overworldPathNode.getPreciseY()] == destinationRegionId) {
                new OverworldEdge(destinationNode, overworldPathNode, 1);
            }
        }

        HashSet<OverworldPathNode> closed = new HashSet<>();
        PriorityQueue<OverworldPathNode> open = new PriorityQueue<>();

        open.add(startingNode);

        float maxDepth = 0;
        while ((maxDepth < maxSearchCost) && (open.size() != 0)) {

            OverworldPathNode current = open.peek();

            if (current == destinationNode) { //we are at destination zone within the destination chunk
                break;
            }

            open.remove(current);
            closed.add(current);

            for (OverworldEdge edge : current.edges) {

                OverworldPathNode neighbor = null;
                if (edge.right == current) {
                    neighbor = edge.left;
                } else if (edge.left == current) {
                    neighbor = edge.right;
                } else
                    assert false;

                float nextStepCost = current.cost + edge.cost;

                // Then this next one is lower cost than any previous encounter with it
                if (nextStepCost < neighbor.cost) {
                    if (open.contains(neighbor)) {
                        open.remove(neighbor);
                    }
                    if (closed.contains(neighbor)) {
                        closed.remove(neighbor);
                    }
                }

                if (!open.contains(neighbor) && !closed.contains(neighbor)) {
                    neighbor.cost = nextStepCost;
                    neighbor.heuristic = heuristic.getCost(overworld, neighbor.getGlobalX(), neighbor.getGlobalY(), globalDestinationX, globalDestinationY);
                    maxDepth = Math.max(maxDepth, neighbor.setParent(current));
                    open.add(neighbor);
                }
            }

        }

        if (destinationNode.parent == null) {
            return null;
        }

        ArrayList<Point2i> path = new ArrayList<>();

        OverworldPathNode target = destinationNode;
        while (target != startingNode) {
            path.add(new Point2i(target.getGlobalX(), target.getGlobalY()));
            target = target.parent;
        }
        path.add(new Point2i(globalStartingX, globalStartingY));

        Collections.reverse(path);

        for (OverworldPathNode node : sourceChunk.overworldPathNodes) {
            node.edges.removeAll(startingNode.edges);
        }

        for (OverworldPathNode node : destinationChunk.overworldPathNodes) {
            node.edges.removeAll(destinationNode.edges);
        }

        return path;
    }

    public void buildEntrances(OverworldExample overworld) {

        // Build Horizontal connections
        for (int y = 0; y < overworld.getYSizeInChunks(); y++) {
            for (int x = 0; x < overworld.getXSizeInChunks() - 1; x++) {

                Chunk leftChunk = overworld.chunks[x][y];
                Chunk rightChunk = overworld.chunks[x + 1][y];

                // Iterate from top to bottom
                int i = 0;
                boolean open = false;
                int start = 0;

                while (i < Chunk.ySize) {

                    if (!leftChunk.ground[Chunk.xSize - 1][i].isBlocking() && !rightChunk.ground[0][i].isBlocking()) {
                        if (!open) {
                            open = true;
                            start = i;
                        }
                    } else if (open) {
                        // close it down

                        OverworldPathNode leftNode = new OverworldPathNode(leftChunk, Chunk.xSize - 1, (i - start) / 2);
                        OverworldPathNode rightNode = new OverworldPathNode(rightChunk, 0, (i - start) / 2);

                        new OverworldEdge(leftNode, rightNode, 1);

                        leftChunk.overworldPathNodes.add(leftNode);
                        rightChunk.overworldPathNodes.add(rightNode);
                        open = false;
                    }
                    i++;
                }
                if (open) {
                    // Close any remaining openings
                    OverworldPathNode leftNode = new OverworldPathNode(leftChunk, Chunk.xSize - 1, (i - start) / 2);
                    OverworldPathNode rightNode = new OverworldPathNode(rightChunk, 0, (i - start) / 2);

                    new OverworldEdge(leftNode, rightNode, 1);


                    leftChunk.overworldPathNodes.add(leftNode);
                    rightChunk.overworldPathNodes.add(rightNode);
                }

            }
        }


        // Build Vertical connections
        for (int x = 0; x < overworld.getXSizeInChunks(); x++) {
            for (int y = 0; y < overworld.getYSizeInChunks() - 1; y++) {
                Chunk top = overworld.chunks[x][y];
                Chunk bottom = overworld.chunks[x][y + 1];

                // Iterate from left to right
                int i = 0;
                boolean open = false;
                int start = 0;

                while (i < Chunk.xSize) {

                    if (!top.ground[i][Chunk.ySize - 1].isBlocking() &&
                            !bottom.ground[i][0].isBlocking()) {
                        if (!open) {
                            open = true;
                            start = i;
                        }
                    } else if (open) {
                        // close it down
                        OverworldPathNode topNode = new OverworldPathNode(top, (i - start) / 2, Chunk.xSize - 1);
                        OverworldPathNode bottomNode = new OverworldPathNode(bottom, (i - start) / 2, 0);

                        new OverworldEdge(topNode, bottomNode, 1);

                        top.overworldPathNodes.add(topNode);
                        bottom.overworldPathNodes.add(bottomNode);

                        open = false;
                    }
                    i++;
                }
                if (open) {
                    // Close any remaining openings
                    OverworldPathNode topNode = new OverworldPathNode(top, (i - start) / 2, Chunk.xSize - 1);
                    OverworldPathNode bottomNode = new OverworldPathNode(bottom, (i - start) / 2, 0);

                    new OverworldEdge(topNode, bottomNode, 1);

                    top.overworldPathNodes.add(topNode);
                    bottom.overworldPathNodes.add(bottomNode);

                }

            }
        }

        // Interconnect the central peices
        for (int x = 0; x < overworld.getXSizeInChunks(); x++) {

            for (int y = 0; y < overworld.getYSizeInChunks(); y++) {
                Chunk chunk = overworld.getChunk(x * Chunk.xSize, y * Chunk.ySize);

                for (OverworldPathNode owpn1 : chunk.overworldPathNodes) {
                    for (OverworldPathNode owpn2 : chunk.overworldPathNodes) {

                        if (chunk.regionIds[owpn1.getPreciseX()][owpn1.getPreciseY()] == chunk.regionIds[owpn1.getPreciseX()][owpn1.getPreciseY()]) {
                            OverworldEdge edge = new OverworldEdge(owpn1, owpn2, 1);
                        }
                    }
                }
            }
        }

    }
}
