package com.stewsters.util.shadow;

public class LitMap2dExample implements LitMap2d {

    private float[][] resistanceMap;
    private float[][] lightMap;
    private int width;
    private int height;

    public LitMap2dExample(float[][] resistanceMap) {
        this.resistanceMap = resistanceMap;

        width = resistanceMap.length;
        height = resistanceMap[0].length;
        lightMap = new float[width][height];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public float getLight(int currentX, int currentY) {
        return lightMap[currentX][currentY];
    }

    @Override
    public float getResistance(int currentX, int currentY) {
        return resistanceMap[currentX][currentY];
    }

    @Override
    public void setLight(int x, int y, float val) {
        lightMap[x][y] = val;
    }


}
