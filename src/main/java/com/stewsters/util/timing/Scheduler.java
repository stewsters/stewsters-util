package com.stewsters.util.timing;

import java.util.LinkedList;

//http://nadako.tumblr.com/post/46340820457/turn-based-time-scheduling

public class Scheduler {
    private LinkedList<Actor> queue;
    private Actor currentActor;
    private boolean currentActorRemoved;

    private int lockCount;


    public Scheduler() {
        queue = new LinkedList<Actor>();
    }

    public void addActor(Actor actor) {
        queue.add(actor);
    }

    public void removeActor(Actor actor) {
        queue.remove(actor);

        if (currentActor == actor) {
            currentActorRemoved = true;
        }
    }

    public void lock() {
        lockCount++;

    }

    public void unlock() {
        if (lockCount == 0) {
            //You have unlocked too much
            assert false;
        }
        lockCount--;
    }

    public boolean tick() {

        if (lockCount > 0)
            return false;

        Actor actor = queue.peek();
        if (actor == null)
            return false;

        while (actor.getEnergy() > 0) {
            currentActor = actor;

            int actionCost = actor.act();
            currentActor = null;

            if (currentActorRemoved) {
                currentActorRemoved = false;
                return true;
            }

            if (actionCost < 0)
                return false;

            actor.removeEnergy(actionCost);

            if (lockCount > 0)
                return false;
        }

        actor.addEnergy(actor.getSpeed());
        queue.add(queue.pop());
        return true;
    }

    public int actorCount() {
        return queue.size();
    }
}
