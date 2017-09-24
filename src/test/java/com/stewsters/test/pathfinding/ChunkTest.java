package com.stewsters.test.pathfinding;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.chunk.ExampleChunk;
import com.stewsters.test.examples.chunk.OverworldExample;
import com.stewsters.test.examples.chunk.OverworldPathfinder;
import com.stewsters.util.math.Point2i;
import com.stewsters.util.pathing.twoDimention.heuristic.ManhattanHeuristic2d;
import com.stewsters.util.pathing.twoDimention.hpa.Chunk2d;
import com.stewsters.util.pathing.twoDimention.hpa.ChunkPathfinder;
import com.stewsters.util.pathing.twoDimention.hpa.OverworldPathNode;
import org.junit.Test;

import java.util.ArrayList;

public class ChunkTest {

    @Test
    public void testRecalculatingChunks() {

        ExampleChunk chunk = new ExampleChunk(0, 0);

        for (int x = 0; x < chunk.getXSize(); x++) {
            for (int y = 0; y < chunk.getYSize(); y++) {

                if ((y == 5 || x == 7) && !(y == 5 && x == 3))
                    chunk.ground[x][y] = new ExampleCellType('X', true);
                else
                    chunk.ground[x][y] = new ExampleCellType('.', false);
            }
        }

        chunk.recalculate();
        for (int y = 0; y < chunk.getXSize(); y++) {
            for (int x = 0; x < chunk.getYSize(); x++) {

                System.out.print(chunk.getRegionId(x, y));
            }
            System.out.println();
        }
    }

    @Test
    public void testChunkPathfinder() {

        int xChunks = 2;
        int yChunks = 2;

        OverworldExample overworldExample = new OverworldExample(xChunks, yChunks);

        final ExampleChunk chunk = overworldExample.getChunk(0, 0);

        ChunkPathfinder chunkPathfinder = new ChunkPathfinder(chunk.getXSize(), chunk.getYSize());
        chunk.recalculate();

        ArrayList<Point2i> points = chunkPathfinder.getPath(
                (int sx, int sy, int tx, int ty) -> !overworldExample.isBlocking(tx, ty),
                (int tx, int ty) -> !overworldExample.isBlocking(tx, ty),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                chunk, 0, 0, chunk.getXSize() - 1, chunk.getYSize() - 1, new ManhattanHeuristic2d(),
                1000, false);

        assert points != null;
        assert points.size() == 31;
    }


    @Test
    public void testCreateOverworld() {

        int xChunks = 3;
        int yChunks = 6;

        OverworldExample overworldExample = new OverworldExample(xChunks, yChunks);
        Chunk2d chunkTest = overworldExample.getChunk(0, 0);

        assert overworldExample.getXSize() == xChunks * chunkTest.getXSize();
        assert overworldExample.getYSize() == yChunks * chunkTest.getYSize();

        OverworldPathfinder pathfinder = new OverworldPathfinder();
        pathfinder.buildEntrances(overworldExample);

        for (int x = 0; x < overworldExample.getXSizeInChunks(); x++) {
            for (int y = 0; y < overworldExample.getYSizeInChunks(); y++) {

                ExampleChunk chunk = overworldExample.getChunk(x * chunkTest.getXSize(), y * chunkTest.getYSize());

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
        Chunk2d chunkTest = overworldExample.getChunk(0, 0);

        assert overworldExample.getXSize() == xChunks * chunkTest.getXSize();
        assert overworldExample.getYSize() == yChunks * chunkTest.getYSize();

        OverworldPathfinder pathfinder = new OverworldPathfinder();
        pathfinder.buildEntrances(overworldExample);

        ArrayList<Point2i> path = pathfinder.getPath(
                (int sx, int sy, int tx, int ty) -> !overworldExample.isBlocking(tx, ty),
                (int x, int y) -> !overworldExample.isBlocking(x, y),
                (int sx, int sy, int tx, int ty) -> 1.0f,
                overworldExample,
                1, 1, overworldExample.getXSize() - 1, overworldExample.getYSize() - 1,
                new ManhattanHeuristic2d(),
                1000);

        assert path != null;
        assert path.get(0).x == 1;
        assert path.get(0).y == 1;
        assert path.get(path.size() - 1).x == overworldExample.getXSize() - 1;
        assert path.get(path.size() - 1).y == overworldExample.getYSize() - 1;


//        ChunkPathfinder chunkPathfinder = new ChunkPathfinder();
//        chunkPathfinder.getPath();
    }

}
