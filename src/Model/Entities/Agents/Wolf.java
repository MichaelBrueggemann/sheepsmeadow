package Model.Entities.Agents;

import java.awt.Color;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.Entities.Agents.Behavior.Actions.*;
import Model.Entities.Agents.Behavior.Actions.ConcreteActions.*;

import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Wolf extends Agent
{

    // class based ID to autoincrement when creating a new agent
    protected static int idCount = 0;

    public Wolf(int energy, ObjectGrid2D grid, MersenneTwisterFast rng, int reproductionDelay)
    {
        // construct all attributes of an "Agent"
        super(Color.gray, energy, grid, rng, reproductionDelay); 

        // add auto-increamenting id for this agent
        idCount++;

        this.id = idCount;

        // Comparator needed to decide the order of two "GeneralAction"s
        Comparator<GeneralAction> generalActionComparator = new GeneralActionComparator();

        // create "Action"s for this agents "ActionList"
        GeneralAction eatSheep = new EatSheep(0);
        GeneralAction reproduceWolf = new ReproduceWolf(1);
        GeneralAction defaultMove = new DefaultMove(2);
        GeneralAction loseEnergy = new LoseEnergy(3);

        this.ruleset = new PriorityQueue<>(generalActionComparator);
        ruleset.add(eatSheep);
        ruleset.add(reproduceWolf);
        ruleset.add(defaultMove);
        ruleset.add(loseEnergy);
    }

    @Override
    public void step(SimState state)
    {
        super.step(state);
    }
}