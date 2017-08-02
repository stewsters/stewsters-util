package com.stewsters.test

import com.stewsters.test.examples.plan.AirShipWorldState
import com.stewsters.test.examples.plan.SwordCombatWorldState
import com.stewsters.util.planner.Action
import com.stewsters.util.planner.Fitness
import com.stewsters.util.planner.Planner
import org.junit.Test

class GroovyPlannerTest {

    /*
    * Groovy closures are different here, testing that they work
     */

    @Test
    public void testPlannerSwordUsage() {

        SwordCombatWorldState startingWorldState = new SwordCombatWorldState();
        int maxCost = 100;

        List<Action<SwordCombatWorldState>> actions = Arrays.asList(
                new Action<>(
                        "Punch",
                        { SwordCombatWorldState w ->
                            return w.opponentsHp > 0 && w.hp > 0;
                        }, { SwordCombatWorldState w ->
                    w.opponentsHp--;
                    if (w.opponentsHp > 0)
                        w.hp--;
                    w.addCost(5);
                    return w;
                }),

                new Action<>(
                        "Stab",
                        { SwordCombatWorldState w ->
                            return w.opponentsHp > 0 && w.hp > 0 && w.hasSword;
                        },
                        { SwordCombatWorldState w ->
                            w.opponentsHp -= 5;
                            if (w.opponentsHp > 0)
                                w.hp--;
                            w.addCost(10);
                            return w;
                        }),

                new Action<>(
                        "Grab Sword",
                        { SwordCombatWorldState w ->
                            return w.opponentsHp > 0 && w.hp > 0 && !w.hasSword;
                        },
                        { SwordCombatWorldState w ->
                            w.hasSword = true;

                            if (w.opponentsHp > 0)
                                w.hp--;

                            w.addCost(10);
                            return w;
                        })
                ,
                new Action<>(
                        "Le Nap",
                        { SwordCombatWorldState w ->
                            return w.opponentsHp <= 0;
                        },
                        { SwordCombatWorldState w ->
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
            plan.get()
                    .collect { it.getName() }
                    .each { println it }

        } else {
            System.out.println("Nope");
            assert false;
        }

    }

}
