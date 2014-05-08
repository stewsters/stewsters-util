package com.stewsters.test.spatial;

import com.stewsters.util.spatial.IntervalKDTree2d;

import java.util.HashSet;
import java.util.Random;

public class Test2d {

    public static void main(String[] args) {
        final int range = 100;
        final int quantity = 10;

        IntervalKDTree2d<Spacecraft2d> spacecrafts = new IntervalKDTree2d<Spacecraft2d>(range, 10);

        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            Spacecraft2d spacecraft = new Spacecraft2d(random.nextInt(2 * range) - range, random.nextInt(2 * range) - range);
            spacecraft.addToTree(spacecrafts);

        }

        HashSet<Spacecraft2d> fillThis = new HashSet<Spacecraft2d>();

        long nanoTime = System.nanoTime();
        fillThis = spacecrafts.getValues(-50, -50, 50, 50, fillThis);
        long finishTime = System.nanoTime();


        for (Spacecraft2d spacecraft : fillThis) {
            System.out.println(spacecraft.toString());

        }

        System.out.println(((double) finishTime - nanoTime) / 1000000000);


        nanoTime = System.nanoTime();
        for (Spacecraft2d spacecraft : fillThis) {
            spacecrafts.remove(spacecraft);
            spacecraft.x++;
            spacecraft.y++;

            spacecraft.addToTree(spacecrafts);
        }
        finishTime = System.nanoTime();
        System.out.println(((double) finishTime - nanoTime) / 1000000000);

    }


}
