package com.stewsters.util.planner;

public interface World<W extends World> {

    W getNext();

    float getCost();

    void setCost(float cost);

    void addCost(float cost);

    W getParentState();

    void setParentState(World<W> current);

    Action getParentAction();

    void setParentAction(Action<W> action);

    boolean meetsPrerequisite(Prerequisite<W> prerequisite);

}
