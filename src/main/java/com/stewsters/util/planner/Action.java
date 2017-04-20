package com.stewsters.util.planner;

public class Action {

    private String name;
    private Prerequisite prerequisite;
    private Effect effect;
    private float cost;

    public Action(String name, Prerequisite prerequisite, Effect effect, float cost) {

        this.name = name;
        this.prerequisite = prerequisite;
        this.effect = effect;
        this.cost = cost;

    }

    public String getName() {
        return name;
    }

    public Prerequisite getPrerequisite() {
        return prerequisite;
    }

    public Effect getEffect() {
        return effect;
    }

    public float getCost() {
        return cost;
    }

}