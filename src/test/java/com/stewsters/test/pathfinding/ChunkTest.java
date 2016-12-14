package com.stewsters.test.pathfinding;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.chunk.Chunk;
import com.stewsters.test.examples.chunk.ChunkPathfinder;
import com.stewsters.test.examples.chunk.Mover;
import com.stewsters.test.examples.chunk.Mover2dV2;
import com.stewsters.test.examples.chunk.OverworldExample;
import com.stewsters.test.examples.chunk.OverworldPathNode;
import com.stewsters.test.examples.chunk.OverworldPathfinder;
import com.stewsters.util.math.Point2i;
import org.junit.Test;

import java.util.ArrayList;

public class ChunkTest {

    @Test
    public void testRecalculatingChunks() {

        Chunk chunk = new Chunk(0, 0);

        for (int x = 0; x < chunk.xSize; x++) {
            for (int y = 0; y < chunk.ySize; y++) {

                if ((y == 5 || x == 7) && !(y == 5 && x == 3))
                    chunk.ground[x][y] = new ExampleCellType('X', true);
                else
                    chunk.ground[x][y] = new ExampleCellType('.', false);
            }
        }

        chunk.recalculate();
        for (int y = 0; y < chunk.ySize; y++) {
            for (int x = 0; x < chunk.xSize; x++) {

                System.out.print(chunk.regionIds[x][y]);
            }
            System.out.println();
        }
    }

    @Test
    public void testChunkPathfinder() {

        final Chunk chunk = new Chunk(0, 0);
        ChunkPathfinder chunkPathfinder = new ChunkPathfinder();
        Mover mover = new Mover(null);

        ArrayList<Point2i> points = chunkPathfinder.getPath(chunk, 0, 0, Chunk.xSize - 1, Chunk.ySize - 1, mover, 1000);

        assert points.size() == 31;
    }


    @Test
    public void testCreateOverworld() {

        int xChunks = 3;
        int yChunks = 6;

        OverworldExample overworldExample = new OverworldExample(xChunks, yChunks);

        assert overworldExample.getXSize() == xChunks * Chunk.xSize;
        assert overworldExample.getYSize() == yChunks * Chunk.ySize;

        OverworldPathfinder pathfinder = new OverworldPathfinder();
        pathfinder.buildEntrances(overworldExample);

        for (int x = 0; x < overworldExample.getXSizeInChunks(); x++) {
            for (int y = 0; y < overworldExample.getYSizeInChunks(); y++) {

                Chunk chunk = overworldExample.getChunk(x * Chunk.xSize, y * Chunk.ySize);

                int expected = 2;
                if (x > 0 && x < overworldExample.getXSizeInChunks() - 1) {
                    expected++;
                }
                if (y > 0 && y < overworldExample.getYSizeInChunks() - 1) {
                    expected++;
                }

                System.out.println(x + ", " + y + " has " + chunk.overworldPathNodes.size() + " nodes. Expected " + expected);
                if (chunk.overworldPathNodes.size() != expected) {
                    assert false;
                }

                for (OverworldPathNode owpn : chunk.overworldPathNodes) {
                    assert owpn.edges.size() >= 2;
                }

            }
        }
    }


    @Test
    public void testPathfindingOnOpenWorld() {
        int xChunks = 20;
        int yChunks = 20;

        OverworldExample overworldExample = new OverworldExample(xChunks, yChunks);

        assert overworldExample.getXSize() == xChunks * Chunk.xSize;
        assert overworldExample.getYSize() == yChunks * Chunk.ySize;

        OverworldPathfinder pathfinder = new OverworldPathfinder();
        pathfinder.buildEntrances(overworldExample);

        Mover2dV2 mover2dV2 = new Mover(overworldExample);

        ArrayList<Point2i> path = pathfinder.getPath(overworldExample,
                1, 1, overworldExample.getXSize() - 1, overworldExample.getYSize() - 1,
                mover2dV2, 1000);

        assert path != null;
        assert path.get(0).x == 1;
        assert path.get(0).y == 1;
        assert path.get(path.size() - 1).x == overworldExample.getXSize() - 1;
        assert path.get(path.size() - 1).y == overworldExample.getYSize() - 1;


//        ChunkPathfinder chunkPathfinder = new ChunkPathfinder();
//        chunkPathfinder.getPath();
    }

}
