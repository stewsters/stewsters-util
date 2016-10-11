package com.stewsters.test.examples.chunk;

import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.pathfinder.AStarHeuristic2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

public class OverworldPathfinder {

    public ArrayList<Point2i> getPath(OverworldExample overworld, int sx, int sy, int tx, int ty, Mover2dV2 mover2d, float maxSearchDistance) {

        AStarHeuristic2d heuristic = mover2d.getHeuristic();
//        boolean allowDiag = mover2d.getDiagonal();

        if (!mover2d.canOccupy(tx, ty))
            return null;

        int xSPrecise = sx % Chunk.xSize;
        int ySPrecise = sy % Chunk.ySize;

        int xTPrecise = tx % Chunk.xSize;
        int yTPrecise = ty % Chunk.ySize;

        Chunk sourceChunk = overworld.getChunk(sx, sy);
        int sourceRegionId = sourceChunk.regionIds[xSPrecise][ySPrecise];

        Chunk destinationChunk = overworld.getChunk(tx, ty);
        int destinationRegionId = destinationChunk.regionIds[xTPrecise][yTPrecise];

        OverworldPathNode startingNode = new OverworldPathNode(sourceChunk, xSPrecise, ySPrecise);
        OverworldPathNode destinationNode = new OverworldPathNode(destinationChunk, xTPrecise,yTPrecise);

        for (OverworldPathNode overworldPathNode : sourceChunk.overworldPathNodes) {

            if (sourceChunk.regionIds[overworldPathNode.x % Chunk.xSize][overworldPathNode.y % Chunk.ySize] == sourceRegionId) {
                new OverworldEdge(startingNode, overworldPathNode, 1);
            }
        }
        for (OverworldPathNode overworldPathNode : destinationChunk.overworldPathNodes) {
            if (sourceChunk.regionIds[overworldPathNode.x % Chunk.xSize][overworldPathNode.y % Chunk.ySize] == destinationRegionId) {
                new OverworldEdge(destinationNode, overworldPathNode, 1);
            }
        }

        HashSet<OverworldPathNode> closed = new HashSet<>();
        PriorityQueue<OverworldPathNode> open = new PriorityQueue<>();

        open.add(startingNode);

        float maxDepth = 0;
        while ((maxDepth < maxSearchDistance) && (open.size() != 0)) {

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

                if (nextStepCost < neighbor.cost) {
                    if (open.contains(neighbor)) {
                        open.remove(neighbor);
                    }
                    if (closed.contains(neighbor)) {
                        closed.remove(neighbor);
                    }
                }

                if (!open.contains(neighbor) && !(closed.contains(neighbor))) {
                    neighbor.cost = nextStepCost;
                    neighbor.heuristic = heuristic.getCost(overworld, neighbor.x, neighbor.y, tx, ty);
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
            path.add(new Point2i(target.x, target.y));
            target = target.parent;
        }
        path.add(new Point2i(sx, sy));

        Collections.reverse(path);
        //TODO: disconnect parents
        return path;
    }

    public void buildEntrances(OverworldExample overworld) {

        // Build Horizontal connections
        for (int y = 0; y < overworld.getYSizeInChunks(); y++) {
            for (int x = 0; x < overworld.getXSizeInChunks() - 1; x++) {

                Chunk left = overworld.chunks[x][y];
                Chunk right = overworld.chunks[x + 1][y];

                // Iterate from top to bottom
                int i = 0;
                boolean open = false;
                int start = 0;

                while (i < Chunk.ySize) {

                    if (!left.ground[Chunk.xSize - 1][i].isBlocking() &&
                            !right.ground[0][i].isBlocking()) {
                        if (!open) {
                            open = true;
                            start = i;
                        }
                    } else if (open) {
                        // close it down

                        OverworldPathNode leftNode = new OverworldPathNode(left, 0, (i - start) / 2);
                        OverworldPathNode rightNode = new OverworldPathNode(right, Chunk.xSize - 1, (i - start) / 2);

                        OverworldEdge edge = new OverworldEdge(leftNode, rightNode, 1);

                        left.overworldPathNodes.add(leftNode);
                        right.overworldPathNodes.add(rightNode);
                        open = false;
                    }
                    i++;
                }
                if (open) {
                    // Close any remaining openings
                    OverworldPathNode leftNode = new OverworldPathNode(left, 0, (i - start) / 2);
                    OverworldPathNode rightNode = new OverworldPathNode(left, Chunk.xSize - 1, (i - start) / 2);

                    OverworldEdge edge = new OverworldEdge(leftNode, rightNode, 1);

                    left.overworldPathNodes.add(leftNode);
                    right.overworldPathNodes.add(rightNode);
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
                        OverworldPathNode topNode = new OverworldPathNode(top, (i - start) / 2, 0);
                        OverworldPathNode bottomNode = new OverworldPathNode(bottom, (i - start) / 2, Chunk.xSize - 1);

                        top.overworldPathNodes.add(topNode);
                        bottom.overworldPathNodes.add(bottomNode);

                        open = false;
                    }
                    i++;
                }
                if (open) {
                    // Close any remaining openings
                    OverworldPathNode topNode = new OverworldPathNode(top, (i - start) / 2, 0);
                    OverworldPathNode bottomNode = new OverworldPathNode(bottom, (i - start) / 2, Chunk.xSize - 1);

                    top.overworldPathNodes.add(topNode);
                    bottom.overworldPathNodes.add(bottomNode);

                }

            }
        }
    }
}
