package Model.Entities.Agents;

import java.awt.Color;

import ec.util.MersenneTwisterFast;
import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Sheep extends Agent
{
    

    public Sheep(int id, int energy, ObjectGrid2D grid, MersenneTwisterFast rng)
    {
        super(id, Color.blue, energy, grid, rng);
    }

    @Override
    public void step(SimState state)
    {

    }

}
