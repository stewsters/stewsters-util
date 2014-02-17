package com.stewsters.test.examples;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.pathing.twoDimention.shared.Mover2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

/**
 *
 */
public class ExampleMap2d implements TileBasedMap2d {

    private final int width;
    private final int height;
    protected CellType[][] ground;

    public ExampleMap2d(int width, int height) {
        this.width = width;
        this.height = height;

        ground = new ExampleCellType[width][height];

        //TODO: move these out, they wont be defined here.
        CellType wall = new ExampleCellType('X', true);
        CellType floor = new ExampleCellType('.', false);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if ((x == 0 || x >= width - 1 || y == 0 || y >= width - 1))
                    ground[x][y] = wall;
                else
                    ground[x][y] = floor;
            }
        }

        //set up some walls

    }

    @Override
    public int getWidthInTiles() {
        return width;
    }

    @Override
    public int getHeightInTiles() {
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
