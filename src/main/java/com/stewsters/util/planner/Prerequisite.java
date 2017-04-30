package com.stewsters.util.planner;

public interface Prerequisite<W> {

    boolean has(W worldState);
}
