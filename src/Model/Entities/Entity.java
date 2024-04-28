package Model.Entities;

import java.awt.Color;

import ec.util.MersenneTwisterFast;
import sim.engine.Steppable;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;

/**
 * Abstract Superclass for all Entities
 */
public abstract class Entity implements Steppable 
{
    protected int id;
    protected Color color;
    // RNG of the simulation object. Used for easy access to random numbers for every entity.
    protected MersenneTwisterFast rng;

    // THis method has to be implemented by each concrete subclass as the "LabledPortrayal2D" class in "MeadowDisplay.java" depends on this function to display an entities label.
    public abstract String toString();

    public Entity(int id, Color color, MersenneTwisterFast rng)
    {
        this.id = id;
        this.color = color;
        this.rng = rng;
    }

    public int getId()
    {
        return this.id;
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
}
