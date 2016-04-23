package com.stewsters.util.shadow.threeDimention;

public interface LitMap3d {

    void setLight(int startx, int starty, int startz, float force);

    int getXSize();

    int getYSize();

    int getZSize();

    float getLight(int currentX, int currentY, int currentZ);

    float getResistance(int currentX, int currentY, int currentZ);

    void addLight(int currentX, int currentY, int currentZ, float bright);
}
