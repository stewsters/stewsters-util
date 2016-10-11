package com.stewsters.test.examples.chunk;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

public class OverworldExample implements ChunkedMap2d, TileBasedMap2d {

    private int regionX;
    private int regionY;

    Chunk[][] chunks;

    public OverworldExample(int xRegionSize, int yRegionSize) {
        regionX = xRegionSize;
        regionY = yRegionSize;
        chunks = new Chunk[regionX][regionY];

        for (int x = 0; x < regionX; x++) {
            for (int y = 0; y < regionY; y++) {
                chunks[x][y] = new Chunk(x, y);
                chunks[x][y].recalculate();
            }
        }
    }


    @Override
    public int getXSize() {
        return regionX * Chunk.xSize;
    }

    @Override
    public int getYSize() {
        return regionY * Chunk.ySize;
    }

    @Override
    public boolean isOutsideMap(int tx, int ty) {
        return (tx < 0 || ty < 0 || tx >= getXSize() || ty >= getYSize());
    }

    @Override
    public void pathFinderVisited(int x, int y) {
    }

    public int getXSizeInChunks() {
        return regionX;
    }

    public int getYSizeInChunks() {
        return regionY;
    }

    public ExampleCellType getCell(int gx, int gy) {
        return chunks[gx / Chunk.xSize][gy / Chunk.ySize].ground[gx % Chunk.xSize][gx % Chunk.ySize];
    }

    public boolean isBlocking(int gx, int gy) {
        return chunks[gx / Chunk.xSize][gy / Chunk.ySize].ground[gx % Chunk.xSize][gx % Chunk.ySize].isBlocking();
    }

    public Chunk getChunk(int gx, int gy) {
        return chunks[gx / Chunk.xSize][gy / Chunk.ySize];
    }
}
