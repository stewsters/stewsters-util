package com.stewsters.util;

//import com.badlogic.gdx.math.MathUtils;

public enum Facing {
    NORTH(0, 1, 0),
    EAST(1, 0, 0),
    SOUTH(0, -1, 0),
    WEST(-1, 0, 0),
    UP(0, 0, 1),
    DOWN(0, 0, -1);

    public int x;
    public int y;
    public int z;

    Facing(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Facing random() {
        switch (MathUtils.getIntInRange(0,5)) {
            case 0:
                return NORTH;
            case 1:
                return EAST;
            case 2:
                return SOUTH;
            case 3:
                return WEST;
            case 4:
                return UP;
            default:
                return DOWN;
        }
    }

}
