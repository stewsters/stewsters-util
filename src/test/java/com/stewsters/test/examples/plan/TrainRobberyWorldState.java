package com.stewsters.test.examples.plan;


import com.stewsters.util.planner.BaseWorldState;
import com.stewsters.util.planner.Prerequisite;
import com.stewsters.util.planner.World;

public class TrainRobberyWorldState extends BaseWorldState implements Comparable<TrainRobberyWorldState>, World {

    public int hp;
//    public int opponentsHp;


    public boolean hasGun;
    public int ammoInGun;
    public int ammoInPocket;

    public static final int maxGunAmmo = 6;

    public boolean onRoof;

    public int[] opponents;
    public int[] treasure;
    public int playerPos;
    public int treasureHeld;

    public TrainRobberyWorldState() {
        parentState = null;
        parentAction = null;
        cost = 0;

        hp = 10;

        hasGun = false;
        ammoInGun = 0;
        ammoInPocket = 0;

        onRoof = false;

        opponents = new int[]{3, 0, 0, 1, 0};
        treasure = new int[]{3, 0, 1, 2, 1};
        playerPos = treasure.length - 1;
        treasureHeld = 0;
    }

    public TrainRobberyWorldState getNext() {
        TrainRobberyWorldState newOne = new TrainRobberyWorldState();
        newOne.parentState = this;
        newOne.parentAction = null;
        newOne.cost = cost;

        newOne.hp = hp;

        newOne.hasGun = hasGun;
        newOne.ammoInGun = ammoInGun;
        newOne.ammoInPocket = ammoInPocket;

        newOne.onRoof = onRoof;

        newOne.opponents = opponents.clone();
        newOne.treasure = treasure.clone();
        newOne.playerPos = playerPos;
        newOne.treasureHeld = treasureHeld;

        return newOne;
    }

    @Override
    public boolean meetsPrerequisite(Prerequisite prerequisite) {
        return prerequisite.has(this);
    }

    @Override
    public int compareTo(TrainRobberyWorldState o) {
        return Float.compare(cost, o.cost);
    }
}
