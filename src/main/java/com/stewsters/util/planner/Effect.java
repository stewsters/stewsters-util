package com.stewsters.util.planner;

public interface Effect<W> {

    W doIt(W worldState);

}
