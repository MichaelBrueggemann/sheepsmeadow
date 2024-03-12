package Model.Entities;

import java.awt.Color;

import sim.engine.Steppable;

/**
 * Abstract Superclass for all Entities
 */
public abstract class Entity implements Steppable 
{
    private int id;
    private Color color;

    public Entity(int id, Color color)
    {
        this.id = id;
        this.color = color;
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
