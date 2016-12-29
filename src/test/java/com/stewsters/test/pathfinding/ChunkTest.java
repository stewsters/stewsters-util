package com.stewsters.test.pathfinding;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.chunk.ChunkPathfinder;
import com.stewsters.test.examples.chunk.ChunkedMover;
import com.stewsters.test.examples.chunk.ExampleChunk;
import com.stewsters.test.examples.chunk.OverworldExample;
import com.stewsters.test.examples.chunk.OverworldPathNode;
import com.stewsters.test.examples.chunk.OverworldPathfinder;
import com.stewsters.test.examples.chunk.TestMover;
import com.stewsters.util.math.Point2i;
import org.junit.Test;

import java.util.ArrayList;

public class ChunkTest {

    @Test
    public void testRecalculatingChunks() {

        ExampleChunk chunk = new ExampleChunk(0, 0);

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

                System.out.print(chunk.getRegionId(x, y));
            }
            System.out.println();
        }
    }

    @Test
    public void testChunkPathfinder() {

        final ExampleChunk chunk = new ExampleChunk(0, 0);
        ChunkPathfinder chunkPathfinder = new ChunkPathfinder();
        TestMover testMover = new TestMover(null);

        ArrayList<Point2i> points = chunkPathfinder.getPath(chunk, 0, 0, ExampleChunk.xSize - 1, ExampleChunk.ySize - 1, testMover, 1000);

        assert points.size() == 31;
    }


    @Test
    public void testCreateOverworld() {

        int xChunks = 3;
        int yChunks = 6;

        OverworldExample overworldExample = new OverworldExample(xChunks, yChunks);

        assert overworldExample.getXSize() == xChunks * ExampleChunk.xSize;
        assert overworldExample.getYSize() == yChunks * ExampleChunk.ySize;

        OverworldPathfinder pathfinder = new OverworldPathfinder();
        pathfinder.buildEntrances(overworldExample);

        for (int x = 0; x < overworldExample.getXSizeInChunks(); x++) {
            for (int y = 0; y < overworldExample.getYSizeInChunks(); y++) {

                ExampleChunk chunk = overworldExample.getChunk(x * ExampleChunk.xSize, y * ExampleChunk.ySize);

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

        assert overworldExample.getXSize() == xChunks * ExampleChunk.xSize;
        assert overworldExample.getYSize() == yChunks * ExampleChunk.ySize;

        OverworldPathfinder pathfinder = new OverworldPathfinder();
        pathfinder.buildEntrances(overworldExample);

        ChunkedMover chunkedMover = new TestMover(overworldExample);

        ArrayList<Point2i> path = pathfinder.getPath(overworldExample,
                1, 1, overworldExample.getXSize() - 1, overworldExample.getYSize() - 1,
                chunkedMover, 1000);

        assert path != null;
        assert path.get(0).x == 1;
        assert path.get(0).y == 1;
        assert path.get(path.size() - 1).x == overworldExample.getXSize() - 1;
        assert path.get(path.size() - 1).y == overworldExample.getYSize() - 1;


//        ChunkPathfinder chunkPathfinder = new ChunkPathfinder();
//        chunkPathfinder.getPath();
    }

}
