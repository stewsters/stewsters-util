package com.stewsters.test.examples.plan;


import com.stewsters.util.planner.BaseWorldState;
import com.stewsters.util.planner.Prerequisite;
import com.stewsters.util.planner.World;

public class AirShipWorldState extends BaseWorldState implements Comparable<AirShipWorldState>, World {

    public boolean atAirship;
    public boolean robotHasGear;
    public int scoredGears;

    public AirShipWorldState() {
        parentState = null;
        parentAction = null;
        cost = 0;

        atAirship = true;
        robotHasGear = false;
        scoredGears = 0;
    }

    public AirShipWorldState getNext() {
        AirShipWorldState newOne = new AirShipWorldState();

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
    public int compareTo(AirShipWorldState o) {
        return Float.compare(cost, o.cost);
    }
}
