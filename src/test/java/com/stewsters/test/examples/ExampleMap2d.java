package com.stewsters.test.examples;

import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 *
 */
public class ExampleMap2d implements TileBasedMap2d {

    private final int width;
    private final int height;
    protected ExampleCellType[][] ground;

    public ExampleMap2d(int width, int height, ExampleCellType baseType) {
        this.width = width;
        this.height = height;

        ground = new ExampleCellType[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                ground[x][y] = baseType;
            }
        }

    }

    @Override
    public int getXSize() {
        return width;
    }

    @Override
    public int getYSize() {
        return height;
    }

    @Override
    public void pathFinderVisited(int x, int y) {

    }

    @Override
    public boolean isBlocked(Mover2d mover, PathNode2d pathNode) {
        return ground[pathNode.x][pathNode.y].isBlocking();
    }

    @Override
    public boolean isBlocked(Mover2d mover, int x, int y) {
        return ground[x][y].isBlocking();
    }

    @Override
    public float getCost(Mover2d mover, int sx, int sy, int tx, int ty) {
        return 1;
    }
}
