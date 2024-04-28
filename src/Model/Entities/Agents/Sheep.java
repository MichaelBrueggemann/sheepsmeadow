package Model.Entities.Agents;

import java.awt.Color;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Agents.Behavior.Actions.GeneralActionComparator;
import Model.Entities.Agents.Behavior.Actions.ConcreteActions.DefaultMove;
import Model.Entities.Agents.Behavior.Actions.ConcreteActions.EatGrass;
import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Sheep extends Agent
{
    public Sheep(int id, int energy, ObjectGrid2D grid, MersenneTwisterFast rng)
    {
        super(id, Color.blue, energy, grid, rng);

        // Comparator needed to decide the order of two "GeneralAction"s
        Comparator<GeneralAction> generalActionComparator = new GeneralActionComparator();

        // create "Action"s for this agents "ActionList"
        GeneralAction eatGrass = new EatGrass(0);
        GeneralAction defaultMove = new DefaultMove(1);
        // ..

        this.ruleset = new PriorityQueue<>(generalActionComparator);
        ruleset.add(eatGrass);
        ruleset.add(defaultMove);
    }

    @Override
    public void step(SimState state)
    {
        super.step(state);
    }

}
