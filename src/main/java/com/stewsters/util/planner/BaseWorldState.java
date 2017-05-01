package com.stewsters.util.planner;

public abstract class BaseWorldState implements World {

    protected World parentState;
    protected Action parentAction;
    protected float cost;

    @Override
    public float getCost() {
        return cost;
    }

    @Override
    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public World getParentState() {
        return parentState;
    }

    @Override
    public void setParentState(World current) {
        this.parentState = current;
    }

    @Override
    public Action getParentAction() {
        return parentAction;
    }

    @Override
    public void setParentAction(Action action) {
        this.parentAction = action;
    }

    @Override
    public void addCost(float cost) {
        this.cost += cost;
    }
}