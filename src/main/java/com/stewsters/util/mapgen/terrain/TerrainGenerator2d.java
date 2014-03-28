package com.stewsters.util.mapgen.terrain;


public class TerrainGenerator2d {

    public long height, width;
    public double seaLevel = 0.3;
    public double freezing = 0.2;
    private NoiseFunction2d elevation;
    private NoiseFunction2d precipitation;
    private NoiseFunction2d drainage;
    private NoiseFunction2d temperature;

    public TerrainGenerator2d() {
        height = 1000;
        width = 1000;

        elevation = new NoiseFunction2d(100, 100, 500, 500);
        precipitation = new NoiseFunction2d(-543, 3125, 1000, 1000);
        drainage = new NoiseFunction2d(-3868, 23664, 100, 100);
        temperature = new NoiseFunction2d(235, 97686, 2000, 2000);
    }


    public char getTerrainAt(int x, int y) {

//        http://blog.kaelan.org/randomly-generated-world-map/

        double altitude = elevation.gen(x, y);
//        double altitudePer = altitude

        double latitude = (y / height) - 0.5; // from -0.5 to 0.5


        double temp =
                temperature.gen(x, y) / 3
                        + altitude / 3
                        + Math.abs(1 / latitude) / 3;


        //TODO: temperature gradient by altitude
        //TODO: temperature gradient by latitude


        // precipitation is boosted by being near warm water

        // precipitation is boosted by temperature


        if (altitude < seaLevel) {
            if (temp < freezing)
                return 'i'; // ice
            else
                return 'w'; // water
        } else {

        }


        return '?';
    }


}
