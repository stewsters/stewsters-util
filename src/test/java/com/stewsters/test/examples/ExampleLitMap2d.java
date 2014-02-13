package com.stewsters.test.examples;

import com.stewsters.util.shadow.LitMap2d;

/**
 *
 */
public class ExampleLitMap2d extends ExampleMap2d implements LitMap2d {

    private final int width;
    private final int height;

    private int turnCounter;

    private float resistance[][];
    private float lightLevel[][];
    private int turnLastUpdated[][];

    public ExampleLitMap2d(int width, int height) {
        super(width, height);
        turnCounter = Integer.MIN_VALUE;
        this.width = width;
        this.height = height;

        turnLastUpdated = new int[width][height];
        resistance = new float[width][height];
        lightLevel = new float[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                turnLastUpdated[x][y] = Integer.MIN_VALUE;
                resistance[x][y] = (x == 0 || x >= width - 1 || y == 0 || y >= width - 1) ? 1f : 0.1f;
                lightLevel[x][y] = 0f;
            }
        }

    }

    @Override
    public void setLight(int x, int y, float force) {
        lightLevel[x][y] = force;
        turnLastUpdated[x][y] = turnCounter;
    }

    @Override
    public float getLight(int x, int y) {
        if (turnLastUpdated[x][y] == turnCounter)
            return lightLevel[x][y];
        else
            return 0;
    }

    @Override
    public float getResistance(int x, int y) {
        return resistance[x][y];
    }

    @Override
    public void addLight(int x, int y, float bright) {
        if (turnCounter == turnLastUpdated[x][y]) {
            lightLevel[x][y] += bright;
        } else {
            turnLastUpdated[x][y] = turnCounter;
            lightLevel[x][y] = bright;
        }
    }

    public void incrementTurn() {

        if (turnCounter == Integer.MAX_VALUE) {
            turnCounter = Integer.MIN_VALUE;
        } else {
            turnCounter++;
        }
    }
}