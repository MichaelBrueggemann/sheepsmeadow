package Model.Entities;

import java.awt.Color;

import ec.util.MersenneTwisterFast;
import sim.engine.Steppable;

/**
 * Abstract Superclass for all Entities
 */
public abstract class Entity implements Steppable 
{
    protected int id;
    protected Color color;
    // RNG of the simulation object. Used for easy access to random numbers for every entity.
    protected MersenneTwisterFast rng;

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

    
}
