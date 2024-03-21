package Model.Entities.Agents;

import java.awt.Color;

import Model.Entities.Objects.Grass;
import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Sheep extends Agent
{
    

    public Sheep(int id, int energy, ObjectGrid2D grid)
    {
        super(id, Color.blue, energy, grid);
        this.addPriorityClass(0, Grass.class); // priority 0
        this.addPriorityClass(1, Sheep.class); // priority 1
        this.addPriorityClass(2, Wolve.class); // priority 2
    }

    @Override
    public void step(SimState state)
    {

    }

}
