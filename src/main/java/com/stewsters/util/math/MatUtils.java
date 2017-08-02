package com.stewsters.util.math;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MatUtils {

    private static Random random;

    public static synchronized void init() {
        if (random == null)
            random = new Random();
    }

    /**
     * @param low  inclusive
     * @param high inclusive
     * @return The integer
     */
    public static int getIntInRange(int low, int high) {
        if (low == high) return low;
        if (random == null) init();
        return random.nextInt(high + 1 - low) + low;
    }

    public static int d(int high) {
        return getIntInRange(1, high);
    }


    public static float getFloatInRange(float low, float high) {
        if (low == high) return low;
        if (random == null) init();
        return (random.nextFloat() * (high - low)) + low;
    }

    public static boolean getBoolean() {
        if (random == null) init();
        return random.nextBoolean();
    }

    public static boolean getBoolean(float chance) {
        if (random == null) init();
        return random.nextFloat() <= chance;
    }

    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public static double euclideanDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    // Manhattan distance with diagonals
    public static int chebyshevDistance(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    public static <E> E rand(ArrayList<E> source) {
        int id = getIntInRange(0, source.size() - 1);
        return source.get(id);
    }

    public static <T> T randVal(T[] source) {
        int id = getIntInRange(0, source.length - 1);
        return source[id];
    }

    public static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }

    public static int min(int a, int b, int c) {
        if (min(a, b) < c) return min(a, b);
        return c;
    }

    public static int max(int a, int b) {
        if (a > b) return a;
        return b;
    }

    public static int pow(int a, int b) {
        if (b > 1) return a * pow(a, b - 1);
        else return a;
    }


    public static int limit(int number, int low, int high) {
        return Math.max(low, Math.min(high, number));
    }

    public static float limit(float number, float low, float high) {
        return Math.max(low, Math.min(high, number));
    }

    public static double limit(double number, double low, double high) {
        return Math.max(low, Math.min(high, number));
    }

    public static double getGauss(double stdDeviation) {
        if (random == null) init();
        return random.nextGaussian() * stdDeviation;
    }

    public static float lerp(float min, float max, float percentage) {
        return min + percentage * (max - min);
    }

    public static double lerp(double min, double max, double percentage) {
        return min + percentage * (max - min);
    }

    public static float unlerp(float min, float max, float val) {
        return (val - min) / (max - min);
    }

    public static double unlerp(double min, double max, double val) {
        return (val - min) / (max - min);
    }


    public static float smootherStep(float edge0, float edge1, float x) {
        // Scale, and clamp x to 0..1 range
        x = Math.max(0, Math.min(1, (x - edge0) / (edge1 - edge0)));
        // Evaluate polynomial
        return x * x * x * (x * (x * 6 - 15) + 10);
    }


    public static String getChoice(Map<String, Integer> choicesMap) {
        int totalChances = 0;
        for (Integer value : choicesMap.values()) {
            totalChances += value;
        }
        int dice = MatUtils.getIntInRange(0, totalChances);
        int runningTotal = 0;
        for (Map.Entry<String, Integer> keyValue : choicesMap.entrySet()) {
            runningTotal += keyValue.getValue();
            if (dice <= runningTotal)
                return keyValue.getKey();
        }
        return null;
    }

    /**
     * Faster version of Math.floor
     *
     * @param x A double
     * @return The int value of the floored double
     */
    public static int fastfloor(double x) {
        return x > 0 ? (int) x : (int) x - 1;
    }

    /**
     * Faster version of Math.floor
     *
     * @param x A float
     * @return The int value of the floored float
     */
    public static int fastfloor(float x) {
        return x > 0 ? (int) x : (int) x - 1;
    }


    /**
     * Calculate light from flashlight
     *
     * @param charge         current charge level
     * @param kChargeMin     0.0f;
     * @param kChargeMax     100.0f;
     * @param kLuminosityMin 0.0f;
     * @param kLuminosityMax 0.9f;
     * @return A double representing the light produced
     */
    public double computeLuminosityFromCharge(double charge, double kChargeMin, double kChargeMax, double kLuminosityMin, double kLuminosityMax) {
        double chargeT = unlerp(kChargeMin, kChargeMax, charge);
        return lerp(kLuminosityMax, kLuminosityMin, Math.pow(chargeT, 4));
    }

    /**
     * Calculate light drop off from flashlight running out of charge
     *
     * @param charge The charge percentage
     * @return A double in the range from 0-1 that represents the percentage of light for that charge
     */
    public double flashlight(double charge) {
        return 1 - Math.pow(1 - charge, 4);
    }

}
