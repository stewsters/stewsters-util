package com.stewsters.util.shadow.twoDimention;

public interface LitMap2d {

    void setLight(int startx, int starty, float force);

    int getXSize();

    int getYSize();

    float getLight(int currentX, int currentY);

    float getResistance(int currentX, int currentY);

    void addLight(int currentX, int currentY, float bright);
}
