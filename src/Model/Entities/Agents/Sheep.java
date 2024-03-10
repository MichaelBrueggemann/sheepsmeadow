package Model.Entities.Agents;

import java.awt.Color;

import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Sheep extends Agent
{
    

    public Sheep(int id, int energy, ObjectGrid2D grid)
    {
        super(id, Color.blue, energy, grid);
    }

    @Override
    public void step(SimState state)
    {

    }

}
