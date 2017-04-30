package com.stewsters.util.planner;


public class WorldState extends BaseWorldState implements Comparable<WorldState>, World {

    public boolean atAirship;
    public boolean robotHasGear;
    public int scoredGears;
    public float cost;

    public WorldState() {

        parentState = null;
        parentAction = null;

        atAirship = true;
        robotHasGear = false;
        scoredGears = 0;
        cost = 0;

    }

    public WorldState getNext() {
        WorldState newOne = new WorldState();

        newOne.atAirship = atAirship;
        newOne.robotHasGear = robotHasGear;
        newOne.scoredGears = scoredGears;
        newOne.cost = cost;

        newOne.parentState = this;
        newOne.parentAction = null;
        return newOne;
    }

    @Override
    public boolean meetsPrerequisite(Prerequisite prerequisite) {
        return prerequisite.has(this);
    }

    @Override
    public int compareTo(WorldState o) {
        return Float.compare(cost, o.cost);
    }
}
