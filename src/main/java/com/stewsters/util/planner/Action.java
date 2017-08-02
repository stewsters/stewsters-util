package com.stewsters.util.planner;

public class Action<W> {

    private String name;
    private Prerequisite<W> prerequisite;
    private Effect<W> effect;

    public Action(String name, Prerequisite<W> prerequisite, Effect<W> effect) {

        this.name = name;
        this.prerequisite = prerequisite;
        this.effect = effect;

    }

    public String getName() {
        return name;
    }

    public Prerequisite<W> getPrerequisite() {
        return prerequisite;
    }

    public Effect<W> getEffect() {
        return effect;
    }

}