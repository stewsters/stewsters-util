package com.stewsters.test;


import com.stewsters.test.examples.ExampleLitMap2d;
import com.stewsters.test.examples.ExampleMap2d;
import com.stewsters.test.examples.ExampleMover2d;
import com.stewsters.util.pathing.twoDimention.searcher.DjikstraSearcher2d;
import com.stewsters.util.pathing.twoDimention.searcher.Objective2d;
import com.stewsters.util.pathing.twoDimention.shared.FullPath2d;
import com.stewsters.util.pathing.twoDimention.shared.PathNode2d;
import com.stewsters.util.shadow.ShadowCaster2d;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShadowCasting2dTest {


    @Test
    public void testShadowCast() {

        ExampleLitMap2d map = new ExampleLitMap2d(10, 10);


        ShadowCaster2d shadowCaster2d = new ShadowCaster2d(map);
        map.incrementTurn();
        shadowCaster2d.recalculateFOV(2,2,10,0.3f);

        for(int x = 0; x < map.getWidthInTiles(); x++){
            for(int y = 0; y < map.getWidthInTiles(); y++){
                System.out.println(map.getLight(x,y));
            }
        }

    }

}
