package com.stewsters.test.math;

import com.stewsters.test.examples.ExampleCellType;
import com.stewsters.test.examples.ExampleMap3d;
import com.stewsters.util.math.Bresenham3d;
import com.stewsters.util.math.Evaluator3d;
import com.stewsters.util.math.Point3i;
import org.junit.Test;

import java.util.ArrayList;

public class Bresenham3dTest {

    @Test
    public void drawLine() {
        ExampleCellType air = new ExampleCellType(' ', false);
        final ExampleMap3d map = new ExampleMap3d(10, 10, 10, air);


        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                for (int z = 0; z < 10; z++) {
                    assert Bresenham3d.open(x, y, z, 4, 4, 4, new Evaluator3d() {
                        @Override
                        public boolean isGood(int sx, int sy, int sz, int tx, int ty, int tz) {
                            return !map.isBlocked(tx, ty, tz);
                        }
                    });

                    assert !Bresenham3d.open(x, y, z, 4, 4, 4, new Evaluator3d() {
                        @Override
                        public boolean isGood(int sx, int sy, int sz, int tx, int ty, int tz) {
                            return map.isBlocked(tx, ty, tz);
                        }
                    });

                    ArrayList<Point3i> arrayList = Bresenham3d.getArray(x, y, z, 4, 4, 4);
                    assert arrayList.size()>=1;
                }
            }
        }

    }
}
