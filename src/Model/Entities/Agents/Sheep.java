package Model.Entities.Agents;

import java.awt.Color;
import java.util.Comparator;
import java.util.PriorityQueue;

import Model.Entities.Agents.Behavior.Actions.*;
import Model.Entities.Agents.Behavior.Actions.ConcreteActions.*;

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
        GeneralAction reproduceSheep = new ReproduceSheep(1);
        GeneralAction defaultMove = new DefaultMove(2);
        GeneralAction loseEnergy = new LoseEnergy(3);
        // ..

        this.ruleset = new PriorityQueue<>(generalActionComparator);
        ruleset.add(eatGrass);
        ruleset.add(reproduceSheep);
        ruleset.add(defaultMove);
        ruleset.add(loseEnergy);
    }

    @Override
    public void step(SimState state)
    {
        super.step(state);
    }

}
