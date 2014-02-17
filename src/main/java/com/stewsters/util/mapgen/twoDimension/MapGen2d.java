package com.stewsters.util.mapgen.twoDimension;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.brush.Brush2d;

import javax.sql.rowset.Predicate;

public class MapGen2d {

    // http://www.roguebasin.com/index.php?title=Designing_Flexible,_Reusable_Algorithms
    public static void fillWithBorder(GeneratedMap2d map, CellType fill, CellType wall) {


        for (int x = 0; x < map.getWidthInTiles(); x++) {
            for (int y = 0; y < map.getHeightInTiles(); y++) {


                if (x == 0 || y == 0 || x >= map.getWidthInTiles() - 1 || y >= map.getHeightInTiles() - 1) {
                    map.setCellTypeAt(x, y, wall);

                } else {
                    map.setCellTypeAt(x, y, fill);
                }
            }
        }

    }


    public static void floodFill(GeneratedMap2d map, int startx, int starty, Predicate predicate, Brush2d brush2d) {

        for (int ix = -1; ix <= 1; ix++) {
            for (int iy = -1; iy <= 1; iy++) {


            }
        }
    }
}
