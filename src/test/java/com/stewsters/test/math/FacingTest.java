package com.stewsters.test.math;

import com.stewsters.util.math.Facing2d;
import org.junit.Test;

public class FacingTest {

    @Test
    public void testRotation() {

        Facing2d facing = Facing2d.NORTH;

        // Left
        facing = facing.left();
        assert facing == Facing2d.NORTHWEST;

        facing = facing.left();
        assert facing == Facing2d.WEST;

        facing = facing.left();
        assert facing == Facing2d.SOUTHWEST;

        facing = facing.left();
        assert facing == Facing2d.SOUTH;

        facing = facing.left();
        assert facing == Facing2d.SOUTHEAST;

        facing = facing.left();
        assert facing == Facing2d.EAST;

        facing = facing.left();
        assert facing == Facing2d.NORTHEAST;

        facing = facing.left();
        assert facing == Facing2d.NORTH;

        // Right
        facing = facing.right();
        assert facing == Facing2d.NORTHEAST;

        facing = facing.right();
        assert facing == Facing2d.EAST;

        facing = facing.right();
        assert facing == Facing2d.SOUTHEAST;

        facing = facing.right();
        assert facing == Facing2d.SOUTH;

        facing = facing.right();
        assert facing == Facing2d.SOUTHWEST;

        facing = facing.right();
        assert facing == Facing2d.WEST;

        facing = facing.right();
        assert facing == Facing2d.NORTHWEST;

        facing = facing.right();
        assert facing == Facing2d.NORTH;


    }

    @Test
    public void testReverse() {
        assert Facing2d.NORTH.reverse() == Facing2d.SOUTH;
        assert Facing2d.NORTHEAST.reverse() == Facing2d.SOUTHWEST;
        assert Facing2d.EAST.reverse() == Facing2d.WEST;
        assert Facing2d.SOUTHEAST.reverse() == Facing2d.NORTHWEST;

        assert Facing2d.SOUTH.reverse() == Facing2d.NORTH;
        assert Facing2d.SOUTHWEST.reverse() == Facing2d.NORTHEAST;
        assert Facing2d.WEST.reverse() == Facing2d.EAST;
        assert Facing2d.NORTHWEST.reverse() == Facing2d.SOUTHEAST;
    }

}
