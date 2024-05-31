package Model.Entities;

import java.awt.Color;

import ec.util.MersenneTwisterFast;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.grid.ObjectGrid2D;

/**
 * Abstract Superclass for all Entities
 */
public abstract class Entity implements Steppable 
{

    protected Color color;
    // RNG of the simulation object. Used for easy access to random numbers for every entity.
    protected MersenneTwisterFast rng;

    // can be used to stop the reallocation of the agent to the schedule
    protected Stoppable scheduleStopper;

    public Entity(Color color, MersenneTwisterFast rng)
    {
        this.color = color;
        this.rng = rng;
    }

    

    public Color getColor()
    {
        return this.color;
    }

    public MersenneTwisterFast getRng() 
    {
      return this.rng;
    }
    
    /**
     * Utility function.
     * Places this entity on the given grid.
     * @param grid grid where the entity will be placed
     * @param x position of the entity on the x axis of the grid
     * @param y position of the entity on the y axis of the grid
     */
    public void addToLocation(ObjectGrid2D grid, int x, int y)
    {
        // place agent on the grid
        grid.set(x, y, this);
    }

    public Stoppable getScheduleStopper() 
    {
      return this.scheduleStopper;
    }

    public void setScheduleStopper(Stoppable value) 
    {
      this.scheduleStopper = value;
    }
}
