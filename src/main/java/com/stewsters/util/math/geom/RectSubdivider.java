package com.stewsters.util.math.geom;

import com.stewsters.util.math.MathUtils;

import java.util.ArrayList;

public class RectSubdivider {


    public static ArrayList<Rect> divide(Rect rect, int minSize) {


        int usableWidth = (rect.x2 - rect.x1) - 2;
        int usableHeight = (rect.y2 - rect.y1) - 2;

        ArrayList<Rect> result = new ArrayList<Rect>();
        if (usableHeight <= minSize * 2 || usableWidth <= minSize * 2) {

            result.add(rect);
            return result;
        }

        //find the larger end, divide
        if (usableWidth > usableHeight) {
            //vertical wall added

            int x = MathUtils.getIntInRange(rect.x1 + minSize, rect.x2 - minSize);

            int y1 = rect.y1;
            int h = rect.y2 - rect.y1;

            Rect child1 = new Rect(rect.x1, y1, x - rect.x1 + 1, h + 1);

            Rect child2 = new Rect(x, y1, rect.x2 - x + 1, h + 1);

            result.addAll(divide(child1, minSize));
            result.addAll(divide(child2, minSize));
            return result;

        } else {
            //horizontal wall added

            int y = MathUtils.getIntInRange(rect.y1 + minSize, rect.y2 - minSize);
            int x1 = rect.x1;
            int w = rect.x2 - rect.x1;

            result.addAll(divide(new Rect(x1, rect.y1, w + 1, y - rect.y1 + 1), minSize));
            result.addAll(divide(new Rect(x1, y, w + 1, rect.y2 - y + 1), minSize));

            return result;
        }
    }


}
