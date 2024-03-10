package Model.Entities.Agents;

import java.awt.Color;

import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Wolve extends Agent
{

    public Wolve(int id, int energy, ObjectGrid2D grid)
    {
        super(id, Color.gray, energy, grid);

    }

    @Override
    public void step(SimState state)
    {

    }
}