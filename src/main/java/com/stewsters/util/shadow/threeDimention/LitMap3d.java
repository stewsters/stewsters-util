package com.stewsters.util.shadow.threeDimention;

public interface LitMap3d {

    public void setLight(int startx, int starty, int startz, float force);

    public int getWidthInTiles();

    public int getHeightInTiles();

    public int getDepthInTiles();

    public float getLight(int currentX, int currentY, int currentZ);

    public float getResistance(int currentX, int currentY, int currentZ);

    public void addLight(int currentX, int currentY, int currentZ, float bright);
}
