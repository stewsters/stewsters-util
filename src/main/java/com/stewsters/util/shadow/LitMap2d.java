package com.stewsters.util.shadow;

public interface LitMap2d {

    public void setLight(int startx, int starty, float force);

    public int getWidth();

    public int getHeight();

    public float getLight(int currentX, int currentY);

    public float getResistance(int currentX, int currentY);
}
