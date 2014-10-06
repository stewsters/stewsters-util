package com.stewsters.test.geom;

import com.stewsters.util.math.geom.Rect;
import com.stewsters.util.math.geom.RectSubdivider;
import org.junit.Test;

import java.util.List;

public class RectSubdividerTest {

    @Test
    public void subdivisionTest() {

        int[][] tiles = new int[10][10];

        // initialize it all
        for (int x = 0; x < tiles[0].length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                tiles[x][y] = 0;
            }

        }

        List<Rect> rectList = RectSubdivider.divide(new Rect(0, 0, 9, 9), 1);

        int val = 1;
        for (Rect rect1 : rectList) {

            val++;
            for (int x = rect1.x1; x <= rect1.x2; x++) {
                for (int y = rect1.y1; y <= rect1.y2; y++) {
                    if (tiles[x][y] != 0) {
                        // already marked
                        tiles[x][y] = -1;
                    } else {
                        tiles[x][y] = val;
                    }
                }
            }


        }


        for (int x = 0; x < tiles[0].length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                if (tiles[x][y] == 0) {
                    // missed
                    assert false;
                }
            }

        }

    }


}