package com.stewsters.util.math.geom;

import com.stewsters.util.math.MatUtils;

import java.util.ArrayList;

public class RectSubdivider {

    public static ArrayList<Rect> divide(Rect rect, int minSize) {


        int usableWidth = (rect.x2 - rect.x1);
        int usableHeight = (rect.y2 - rect.y1);

        ArrayList<Rect> result = new ArrayList<Rect>();

        // No division
        if (usableHeight <= minSize * 2 || usableWidth <= minSize * 2) {
            result.add(rect);
            return result;
        }

        // find the larger end, divide
        if (usableWidth > usableHeight) {
            // vertical split added

            int x = MatUtils.getIntInRange(rect.x1 + minSize, rect.x2 - minSize);

            // left
            result.addAll(divide(new Rect(rect.x1, rect.y1, x, rect.y2), minSize));

            //right
            result.addAll(divide(new Rect(x + 1, rect.y1, rect.x2, rect.y2), minSize));
            return result;

        } else {
            // horizontal split added

            int y = MatUtils.getIntInRange(rect.y1 + minSize, rect.y2 - minSize);

            // top
            result.addAll(divide(new Rect(rect.x1, rect.y1, rect.x2, y), minSize));

            // bottom
            result.addAll(divide(new Rect(rect.x1, y + 1, rect.x2, rect.y2), minSize));

            return result;
        }
    }


}
