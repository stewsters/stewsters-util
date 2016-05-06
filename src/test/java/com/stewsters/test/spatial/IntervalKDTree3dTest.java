package com.stewsters.test.spatial;

import com.stewsters.test.examples.Spacecraft3d;
import com.stewsters.util.spatial.IntervalKDTree3d;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class IntervalKDTree3dTest {

    @Test
    public void testCollision() {
        final int range = 10000;
        final int quantity = 1000000;

        IntervalKDTree3d<Spacecraft3d> spacecrafts = new IntervalKDTree3d<Spacecraft3d>(range, 10);

        Random random = new Random();
        for (int i = 0; i < quantity; i++) {
            Spacecraft3d spacecraft = new Spacecraft3d(random.nextInt(2 * range) - range, random.nextInt(2 * range) - range, random.nextInt(2 * range) - range);
            spacecraft.addToTree(spacecrafts);

        }

        HashSet<Spacecraft3d> fillThis = new HashSet<Spacecraft3d>();

        long nanoTime = System.nanoTime();
        fillThis = spacecrafts.getValues(-500, -500, -500, 500, 500, 500, fillThis);
        long finishTime = System.nanoTime();

        List<Spacecraft3d> fillThisToo = new LinkedList<Spacecraft3d>();
        spacecrafts.getValues(-500, -500, -500, 500, 500, 500, fillThisToo);

        assert fillThis.size() == fillThisToo.size();


        for (Spacecraft3d spacecraft : fillThis) {
            System.out.println(spacecraft.toString());

        }

        System.out.println(((double) finishTime - nanoTime) / 1000000000);


        nanoTime = System.nanoTime();
        for (Spacecraft3d spacecraft : fillThis) {
            spacecrafts.remove(spacecraft);
            spacecraft.x++;
            spacecraft.y++;
            spacecraft.z--;
            spacecraft.addToTree(spacecrafts);
        }
        finishTime = System.nanoTime();
        System.out.println(((double) finishTime - nanoTime) / 1000000000);

    }


}
