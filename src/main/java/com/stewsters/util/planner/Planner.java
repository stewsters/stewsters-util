package com.stewsters.util.planner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

public class Planner {

    public static Optional<List<Action>> plan(WorldState startingState, Fitness fitness, List<Action> actions, int maxCost) {

        ArrayList<WorldState> endState = new ArrayList<>();

        PriorityQueue<WorldState> openList = new PriorityQueue<>();
        openList.add(startingState);

        // while we have a worldstate in the open
        while (openList.size() > 0) {
            WorldState current = openList.poll();
            actions.stream()
                    .filter(it -> it.getPrerequisite().has(current))
                    .forEach(action -> {
                        WorldState next = new WorldState(current);
                        action.getEffect().doIt(next);
                        next.cost = current.cost + action.getCost(); //TODO: this may want to be in the effect
                        next.parentAction = action;
                        next.parentState = current;
                        if (next.cost <= maxCost) {
                            openList.add(next);
                        } else {
                            endState.add(next);
                        }
                    });
        }

        Optional<WorldState> optimalState = endState.stream()
                .max((thing1, thing2) -> Float.compare(fitness.fitness(thing1), fitness.fitness(thing2)));

        if (optimalState.isPresent()) {

            WorldState w = optimalState.get();
            ArrayList<Action> plan = new ArrayList<Action>();

            while (w.parentState != null) {
                plan.add(w.parentAction);
                w = w.parentState;
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
