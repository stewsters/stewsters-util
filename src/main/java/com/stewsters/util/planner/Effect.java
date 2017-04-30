package com.stewsters.util.planner;

public interface Effect<W> {

    WorldState doIt(W worldState);

}
