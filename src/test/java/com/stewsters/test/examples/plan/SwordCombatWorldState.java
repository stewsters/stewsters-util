package com.stewsters.test.examples.plan;


import com.stewsters.util.planner.BaseWorldState;
import com.stewsters.util.planner.Prerequisite;
import com.stewsters.util.planner.World;

public class SwordCombatWorldState extends BaseWorldState implements Comparable<SwordCombatWorldState>, World {

    public int hp;
    public int opponentsHp;
    public boolean hasSword;

    public SwordCombatWorldState() {
        parentState = null;
        parentAction = null;
        cost = 0;

        hp = 10;
        opponentsHp = 10;
        hasSword = false;
    }

    public SwordCombatWorldState getNext() {
        SwordCombatWorldState newOne = new SwordCombatWorldState();
        newOne.parentState = this;
        newOne.parentAction = null;
        newOne.cost = cost;

        newOne.hp = hp;
        newOne.opponentsHp = opponentsHp;
        newOne.hasSword = hasSword;

        return newOne;
    }

    @Override
    public boolean meetsPrerequisite(Prerequisite prerequisite) {
        return prerequisite.has(this);
    }

    @Override
    public int compareTo(SwordCombatWorldState o) {
        return Float.compare(cost, o.cost);
    }
}
