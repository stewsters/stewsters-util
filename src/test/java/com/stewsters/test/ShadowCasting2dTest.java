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

        printMap(map,10);

        assert map.getLight(30,30) == 0;
        assert map.getLight(10,10) > 0.1;
        assert map.getLight(10,10) < 1;
        assert map.getLight(5,5) == 1;
    }

    @Test
    public void testTurnsLights() {

        ExampleLitMap2d map = new ExampleLitMap2d(50, 50);

        ShadowCaster2d shadowCaster2d = new ShadowCaster2d(map);
        map.incrementTurn();
        shadowCaster2d.recalculateFOV(5, 5, 10, 0.3f);
        assert map.getLight(5,5) > 0.9;

        map.incrementTurn();
        shadowCaster2d.recalculateFOV(20, 20, 10, 0.3f);
        assert map.getLight(5,5)  > 0.9;

        printMap(map,10);

    }

    private void printMap(ExampleLitMap2d map, float scaler){
        for (int x = 0; x < map.getWidthInTiles(); x++) {
            for (int y = 0; y < map.getWidthInTiles(); y++) {
                int val = (int)( map.getLight(x, y) * 10);
                System.out.print(val);
            }
            System.out.println();
        }
    }

}
