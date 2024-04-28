package Model.Entities.Agents;

import java.awt.Color;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.Entities.Agents.Behavior.Actions.GeneralActionComparator;
import Model.Entities.Agents.Behavior.Actions.ConcreteActions.DefaultMove;
import ec.util.MersenneTwisterFast;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;

import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Wolf extends Agent
{

    public Wolf(int id, int energy, ObjectGrid2D grid, MersenneTwisterFast rng)
    {

        // construct all attributes of an "Agent"
        super(id, Color.gray, energy, grid, rng);

        // Comparator needed to decide the order of two "GeneralAction"s
        Comparator<GeneralAction> generalActionComparator = new GeneralActionComparator();

        // create "Action"s for this agents "ActionList"
        GeneralAction defaultMove = new DefaultMove(0);
        // ..

        this.ruleset = new PriorityQueue<>(generalActionComparator);
        ruleset.add(defaultMove);
    }

    @Override
    public void step(SimState state)
    {
        super.step(state);
    }
}