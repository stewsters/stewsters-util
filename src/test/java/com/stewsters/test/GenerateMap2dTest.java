package com.stewsters.test;

import com.stewsters.test.examples.ExampleGeneretedMap2d;
import com.stewsters.test.examples.ExampleLitMap2d;
import com.stewsters.util.mapgen.twoDimension.MapGen2d;
import org.junit.Test;

public class GenerateMap2dTest {

    @Test
    public void testGeneration(){



        ExampleGeneretedMap2d exampleGeneretedMap2d = new ExampleGeneretedMap2d(20,20);

//        exampleGeneretedMap2d

        MapGen2d.fillWithBorder(exampleGeneretedMap2d, );

    }



    private void printMap(ExampleGeneretedMap2d exampleGeneretedMap2d) {

        for (int y = 0; y < exampleGeneretedMap2d.getWidthInTiles(); y++) {
            for (int x = 0; x < exampleGeneretedMap2d.getWidthInTiles(); x++) {

                System.out.print(exampleGeneretedMap2d.getCellTypeAt(x,y).getGlyph());
            }
            System.out.println();
        }
    }
}
