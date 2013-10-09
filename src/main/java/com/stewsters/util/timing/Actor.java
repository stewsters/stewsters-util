package com.stewsters.util.timing;

public interface Actor {

    public int getEnergy();

    public void removeEnergy(int actionCost);

    public void addEnergy(int speed);

    public int getSpeed();

    public int act();

}
