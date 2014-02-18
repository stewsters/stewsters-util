package com.stewsters.test;

import com.stewsters.util.mapgen.terrain.TerrainGenerator2d;
import org.junit.Test;


public class TerrainTest {

    @Test
    public void terrainGenTest() {
        TerrainGenerator2d terrainGenerator2d = new TerrainGenerator2d();

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                System.out.print(terrainGenerator2d.getTerrainAt(x* 10, y* 10));
            }
            System.out.println();
        }

    }
}
