package com.stewsters.util.planner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class Planner<W extends World> {

    public Optional<List<Action>> plan(W startingState, Fitness<W> fitness, List<Action<W>> actions, int maxCost) {

        ArrayList<W> endState = new ArrayList<>();

        PriorityQueue<W> openList = new PriorityQueue<>();
        openList.add(startingState);

        // while we have a worldstate in the open
        while (openList.size() > 0) {
            World<W> current = openList.poll();
            actions.stream()
                    .filter(it -> current.meetsPrerequisite(it.getPrerequisite()))
                    .forEach(action -> {
                        W next = current.getNext();
                        action.getEffect().doIt(next);
                        next.setParentAction(action);
                        next.setParentState(current);
                        if (next.getCost() <= maxCost) {
                            openList.add(next);
                        } else {
                            endState.add(next);
                        }
                    });
        }

        Optional<W> optimalState = endState.stream()
                .max((thing1, thing2) -> Float.compare(fitness.fitness(thing1), fitness.fitness(thing2)));

        if (optimalState.isPresent()) {

            World<W> w = optimalState.get();
            ArrayList<Action> plan = new ArrayList<>();

            while (w.getParentState() != null) {
                plan.add(w.getParentAction());
                w = w.getParentState();
            }
            Collections.reverse(plan);

            return Optional.of(plan);
        } else {

            // planning sucks
            return Optional.empty();
        }
        // make new worldstats nodes, and their prices.  Expand the most fitness for least cost first?

    }
}
