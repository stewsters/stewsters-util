package com.stewsters.util.planner;

public interface World<W extends World> {
    W getNext();

    float getCost();

    void setCost(float cost);

    void setParentAction(Action<W> action);

    void setParentState(World<W> current);

    W getParentState();

    Action getParentAction();

    boolean meetsPrerequisite(Prerequisite<W> prerequisite);
}
