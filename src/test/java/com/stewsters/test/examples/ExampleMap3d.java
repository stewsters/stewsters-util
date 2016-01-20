package com.stewsters.test.examples;

import com.stewsters.util.pathing.threeDimention.shared.PathNode3d;
import com.stewsters.util.pathing.threeDimention.shared.TileBasedMap3d;

/**
 *
 */
public class ExampleMap3d implements TileBasedMap3d {

    private final int width;
    private final int height;
    private final int depth;
    private boolean ground[][][];

    public ExampleMap3d(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        ground = new boolean[width][height][depth];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < height; z++) {
                    ground[x][y][z] = (x == 0 || x >= width - 1 || y == 0 || y >= width - 1 || z == 0 || (z >= depth - 1));
                }
            }
        }

        //set up some walls

    }

    public void setTile(int x, int y, int z, boolean blocks) {
        ground[x][y][z] = blocks;
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
    public int getZSize() {
        return depth;
    }

    @Override
    public void pathFinderVisited(int x, int y, int z) {

    }

    public boolean isBlocked(PathNode3d pathNode) {
        return isBlocked(pathNode.x, pathNode.y, pathNode.z);
    }

    public boolean isBlocked(int x, int y, int z) {
        if (x < 0 || x >= getXSize() || y < 0 || y >= getYSize() || z < 0 || z >= getZSize())
            return false;
        return ground[x][y][z];
    }


}
