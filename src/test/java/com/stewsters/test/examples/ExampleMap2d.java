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
    private boolean ground[][];

    public ExampleMap2d(int width, int height) {
        this.width = width;
        this.height = height;

        ground = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                ground[x][y] = (x == 0 || x >= width - 1 || y == 0 || y >= width - 1);
            }
        }

        //set up some walls

    }

    @Override
    public int getWidthInTiles() {
        return width;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getHeightInTiles() {
        return height;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pathFinderVisited(int x, int y) {

    }

    @Override
    public boolean blocked(Mover2d mover, PathNode2d pathNode) {
        return ground[pathNode.x][pathNode.y];
    }

    @Override
    public float getCost(Mover2d mover, int sx, int sy, int tx, int ty) {
        return 1;
    }
}
