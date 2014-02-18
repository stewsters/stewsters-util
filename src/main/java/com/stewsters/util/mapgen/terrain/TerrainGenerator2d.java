package com.stewsters.util.mapgen.terrain;


public class TerrainGenerator2d {

    public long height, width;

    private NoiseFunction elevation;
    private NoiseFunction precipitation;
    private NoiseFunction drainage;
    private NoiseFunction temperature;

    public double seaLevel = 0.3;
    public double freezing = 0.2;

    public TerrainGenerator2d() {
        height = 1000;
        width = 1000;

        elevation = new NoiseFunction(100, 100, 500, 500);
        precipitation = new NoiseFunction(-543, 3125, 1000, 1000);
        drainage = new NoiseFunction(-3868, 23664, 100, 100);
        temperature = new NoiseFunction(235, 97686, 2000, 2000);
    }


    public char getTerrainAt(int x, int y) {
        double altitude = elevation.gen(x, y);

        double temp = temperature.gen(x, y);
        //TODO: temperature gradient by altitude
        //TODO: temperature gradient by latitude


        if (altitude < seaLevel) {
            if(temp < freezing)
                return 'i'; // ice
            else
                return 'w'; // water
        } else{

        }


        return '?';
    }


}
