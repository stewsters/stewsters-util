package com.stewsters.util.mapgen.twoDimension;

import com.stewsters.util.mapgen.CellType;
import com.stewsters.util.mapgen.twoDimension.brush.Brush2d;
import com.stewsters.util.mapgen.twoDimension.predicate.CellPredicate2d;
import com.stewsters.util.math.Point2i;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * http://www.roguebasin.com/index.php?title=Designing_Flexible,_Reusable_Algorithms
 */
public class MapGen2d {

    /**
     * Fills an area with a border
     *
     * @param map
     * @param fill
     * @param wall
     */
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


    public static void fill(GeneratedMap2d map2d, CellPredicate2d predicate, Brush2d brush2d) {
        for (int ix = 0; ix < map2d.getWidthInTiles(); ix++) {
            for (int iy = 0; iy < map2d.getHeightInTiles(); iy++) {
                if (predicate.belongs(map2d, ix, iy)) {
                    brush2d.draw(map2d, ix, iy);
                }

            }
        }
    }


    /**
     * Flood fills on things that fit the predicate
     *
     * @param map
     * @param start
     * @param predicate
     * @param brush2d
     */
    public static void floodFill(GeneratedMap2d map, Point2i start, CellPredicate2d predicate, Brush2d brush2d) {

        LinkedList<Point2i> todo = new LinkedList<Point2i>();
        LinkedList<Point2i> match = new LinkedList<Point2i>();
        HashSet<Point2i> done = new HashSet<Point2i>();

        todo.push(start);

        Point2i p;
        while (todo.size() > 0) {
            p = todo.pop();

            if (!done.contains(p) && predicate.belongs(map, p.x, p.y)) {

                match.add(p);

                //todo: done list?
                if (p.x > 0)
                    todo.push(new Point2i(p.x - 1, p.y));
                if (p.x < map.getWidthInTiles() - 1)
                    todo.push(new Point2i(p.x + 1, p.y));
                if (p.y > 0)
                    todo.push(new Point2i(p.x, p.y - 1));
                if (p.y < map.getHeightInTiles() - 1)
                    todo.push(new Point2i(p.x, p.y + 1));
            }
            done.add(p);
        }

        // Goes over the whole map replacing a cell satisfying the predicate with the brush contents.
        for (Point2i point2i : match) {
            brush2d.draw(map, point2i.x, point2i.y);
        }

    }
}
