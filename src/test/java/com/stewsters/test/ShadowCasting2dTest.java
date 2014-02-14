package com.stewsters.test;


import com.stewsters.test.examples.ExampleLitMap2d;
import com.stewsters.util.shadow.ShadowCaster2d;
import org.junit.Test;

public class ShadowCasting2dTest {


    @Test
    public void testShadowCast() {

        ExampleLitMap2d map = new ExampleLitMap2d(50, 50);
        ShadowCaster2d shadowCaster2d = new ShadowCaster2d(map);

        map.incrementTurn();
        shadowCaster2d.recalculateFOV(5, 5, 10, 0.3f);

        printMap(map);

        assert map.getLight(30, 30) == 0;
        assert map.getLight(10, 10) > 0.1;
        assert map.getLight(10, 10) < 1;

        System.out.println(map.getLight(5, 5));
//        assert map.getLight(5, 5) == 10;
    }

    @Test
    public void testTurnsLights() {

        ExampleLitMap2d map = new ExampleLitMap2d(50, 50);

        ShadowCaster2d shadowCaster2d = new ShadowCaster2d(map);
        map.incrementTurn();
        shadowCaster2d.recalculateFOV(5, 5, 10, 0.3f);
        System.out.println(map.getLight(5, 5));
        assert map.getLight(5, 5) > 0.3;

        map.incrementTurn();
        shadowCaster2d.recalculateFOV(20, 20, 10, 0.3f);

        assert map.getLight(5, 5) == 0;

        printMap(map);

    }

    @Test
    public void testWallBlocksLights() {

        ExampleLitMap2d map = new ExampleLitMap2d(50, 50);

        ShadowCaster2d shadowCaster2d = new ShadowCaster2d(map);

        int x = 10;
        for (int y = 12; y < 20; y++) {
            map.resistance[x][y] = 1f;
        }
        for (int y = 1; y < 10; y++) {
            map.resistance[x][y] = 1f;
        }

        map.incrementTurn();

        shadowCaster2d.recalculateFOV(8, 12, 10, 0.3f);
//        System.out.println(map.getLight(12,9));
        assert map.getLight(9, 12) > 0.3;
        assert map.getLight(11, 12) == 0f;

        printMap(map);
    }


    private void printMap(ExampleLitMap2d map) {

        float max = Float.MIN_VALUE;
        for (int y = 0; y < map.getWidthInTiles(); y++) {
            for (int x = 0; x < map.getWidthInTiles(); x++) {

                if (map.getLight(x, y) > max)
                    max = map.getLight(x, y);
            }
        }

        for (int y = 0; y < map.getWidthInTiles(); y++) {
            for (int x = 0; x < map.getWidthInTiles(); x++) {

//                int val = (int) (map.getLight(x, y)/ max );
                int val = (map.getLight(x, y) > 0) ? 1 : 0;

                System.out.print(val);
            }
            System.out.println();
        }
    }

}
