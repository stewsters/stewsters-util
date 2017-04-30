package com.stewsters.util.planner;

public class Action<W> {

    private String name;
    private Prerequisite<W> prerequisite;
    private Effect<W> effect;
    private float cost;

    public Action(String name, Prerequisite<W> prerequisite, Effect<W> effect, float cost) {

        this.name = name;
        this.prerequisite = prerequisite;
        this.effect = effect;
        this.cost = cost;

    }

    public String getName() {
        return name;
    }

    public Prerequisite<W> getPrerequisite() {
        return prerequisite;
    }

    public Effect getEffect() {
        return effect;
    }

    public float getCost() {
        return cost;
    }

}