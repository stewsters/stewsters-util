package com.stewsters.util.shadow;


public class BresenhamLOS2d {
    Map2d map2d;

    public BresenhamLOS2d(LitMap2d map2d) {
        this.map2d = map2d;
    }


    private boolean los(int x1,int y1,int x2,int y2){
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            if(map2d.ge)
            framebuffer.setPixel(x1, y1, Vec3.one);

            if (x1 == x2 && y1 == y2) {
                break;
            }

            int e2 = 2 * err;

            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }

            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }
}
