package com.stewsters.util.shadow.twoDimention;

public interface LitMap2d {

    public void setLight(int startx, int starty, float force);

    public int getWidthInTiles();

    public int getHeightInTiles();

    public float getLight(int currentX, int currentY);

    public float getResistance(int currentX, int currentY);

    public void addLight(int currentX, int currentY, float bright);
}