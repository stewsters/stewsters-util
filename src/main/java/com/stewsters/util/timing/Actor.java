package com.stewsters.util.timing;

public interface Actor {

    int getEnergy();

    void removeEnergy(int actionCost);

    void addEnergy(int speed);

    int getSpeed();

    int act();

}
