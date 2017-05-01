package com.stewsters.test.planner;

import com.stewsters.test.examples.plan.AirShipWorldState;
import com.stewsters.test.examples.plan.SwordCombatWorldState;
import com.stewsters.util.planner.Action;
import com.stewsters.util.planner.Fitness;
import com.stewsters.util.planner.Planner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlannerTest {


    @Test
    public void testPlannerOnGame() {

        AirShipWorldState startingWorldState = new AirShipWorldState();
        startingWorldState.robotHasGear = true;
        int maxCost = 100;

        List<Action<AirShipWorldState>> actions = Arrays.asList(
                new Action<>(
                        "Load Gear",
                        (AirShipWorldState w) -> {
                            return !w.robotHasGear && !w.atAirship;
                        },
                        (AirShipWorldState w) -> {
                            w.robotHasGear = true;
                            w.addCost(10);
                            return w;
                        }),

                new Action<>(
                        "Place Gear",
                        (AirShipWorldState w) -> {
                            return w.robotHasGear && w.atAirship;
                        },
                        (AirShipWorldState w) -> {
                            w.robotHasGear = false;
                            w.scoredGears++;
                            w.addCost(10);
                            return w;
                        }),

                new Action<>(
                        "Go To Airship",
                        (AirShipWorldState w) -> {
                            return !w.atAirship;
                        },
                        (AirShipWorldState w) -> {
                            w.atAirship = true;
                            w.addCost(10);
                            return w;
                        }),

                new Action<>(
                        "Go To Loading",
                        (AirShipWorldState w) -> {
                            return w.atAirship;
                        },
                        (AirShipWorldState w) -> {
                            w.atAirship = false;
                            w.addCost(10);
                            return w;
                        })
        );

        Planner p = new Planner<AirShipWorldState>();

        Optional<List<Action>> plan = p.plan(startingWorldState,
                new Fitness<AirShipWorldState>() {
                    @Override
                    public float fitness(AirShipWorldState worldState) {
                        return worldState.scoredGears - (worldState.getCost() / 1000f); // makes it a little more greedy
                    }
                },
                actions,
                maxCost
        );

        if (plan.isPresent()) {
            plan.get().stream()
                    .map(Action::getName)
                    .forEach(System.out::println);
        } else {
            System.out.println("Nope");
        }

    }


    @Test
    public void testPlannerSwordUsage() {

        SwordCombatWorldState startingWorldState = new SwordCombatWorldState();
        int maxCost = 400;

        List<Action<SwordCombatWorldState>> actions = Arrays.asList(
                new Action<>(
                        "Punch",
                        (SwordCombatWorldState w) -> {
                            return w.opponentsHp > 0 && w.hp > 0;
                        },
                        (SwordCombatWorldState w) -> {
                            w.opponentsHp--;
                            if (w.opponentsHp > 0)
                                w.hp--;
                            w.addCost(5);
                            return w;
                        }),

                new Action<>(
                        "Stab",
                        (SwordCombatWorldState w) -> {
                            return w.opponentsHp > 0 && w.hp > 0 && w.hasSword;
                        },
                        (SwordCombatWorldState w) -> {
                            w.opponentsHp -= 5;
                            if (w.opponentsHp > 0)
                                w.hp--;
                            w.addCost(10);
                            return w;
                        }),

                new Action<>(
                        "Grab Sword",
                        (SwordCombatWorldState w) -> {
                            return w.opponentsHp > 0 && w.hp > 0 && !w.hasSword;
                        },
                        (SwordCombatWorldState w) -> {
                            w.hasSword = true;

                            if (w.opponentsHp > 0)
                                w.hp--;

                            w.addCost(10);
                            return w;
                        })
                ,
                new Action<>(
                        "Le Nap",
                        (SwordCombatWorldState w) -> {
                            return w.opponentsHp <=0;
                        },
                        (SwordCombatWorldState w) -> {
                            w.addCost(10);
                            return w;
                        })
        );

        Planner p = new Planner<AirShipWorldState>();

        Optional<List<Action>> plan = p.plan(startingWorldState,
                new Fitness<SwordCombatWorldState>() {
                    @Override
                    public float fitness(SwordCombatWorldState worldState) {
                        return (worldState.opponentsHp <= 0) ? worldState.hp : 0;
                    }
                },
                actions,
                maxCost
        );

        if (plan.isPresent()) {
            plan.get().stream()
                    .map(Action::getName)
                    .forEach(System.out::println);
        } else {
            System.out.println("Nope");
        }

    }


}
