package Model.Entities.Agents;

import java.awt.Color;

import Model.Entities.Entity;

/**
 * This is a abstract class defining general behavior and attributes of all Agents.
 */
public class Agent extends Entity
{
    private int energy;

    public Agent(int id, Color color, int energy)
    {
        super(id, color);
        this.energy = energy;
    }
    
    public int getEnergy()
    {
        return this.energy;
    }
}
