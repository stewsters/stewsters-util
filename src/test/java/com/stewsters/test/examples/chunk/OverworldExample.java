package com.stewsters.test.examples.chunk;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.util.pathing.twoDimention.hpa.ChunkedMap2d;
import com.stewsters.util.pathing.twoDimention.shared.TileBasedMap2d;

public class OverworldExample implements ChunkedMap2d {

    ExampleChunk[][] chunks;
    private int regionX;
    private int regionY;

    public OverworldExample(int xRegionSize, int yRegionSize) {
        regionX = xRegionSize;
        regionY = yRegionSize;
        chunks = new ExampleChunk[regionX][regionY];

        for (int x = 0; x < regionX; x++) {
            for (int y = 0; y < regionY; y++) {
                chunks[x][y] = new ExampleChunk(x, y);
                chunks[x][y].recalculate();
            }
        }
    }


    @Override
    public int getXSize() {
        return regionX * ExampleChunk.xSize;
    }

    @Override
    public int getYSize() {
        return regionY * ExampleChunk.ySize;
    }

    @Override
    public boolean isOutsideMap(int tx, int ty) {
        return (tx < 0 || ty < 0 || tx >= getXSize() || ty >= getYSize());
    }

    public int getXSizeInChunks() {
        return regionX;
    }

    public int getYSizeInChunks() {
        return regionY;
    }

    public ExampleCellType getCell(int gx, int gy) {
        return chunks[gx / ExampleChunk.xSize][gy / ExampleChunk.ySize].ground[gx % ExampleChunk.xSize][gx % ExampleChunk.ySize];
    }

    public boolean isBlocking(int gx, int gy) {
        return chunks[gx / ExampleChunk.xSize][gy / ExampleChunk.ySize].ground[gx % ExampleChunk.xSize][gx % ExampleChunk.ySize].isBlocking();
    }

    public ExampleChunk getChunk(int gx, int gy) {
        return chunks[gx / ExampleChunk.xSize][gy / ExampleChunk.ySize];
    }
}
