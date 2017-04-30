package com.stewsters.util.planner;

public interface Fitness<W extends World> {

    float fitness(W worldState);

}
