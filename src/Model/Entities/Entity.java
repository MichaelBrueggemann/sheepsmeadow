/*
Copyright (C) 2024 Michael Brüggemann

This file is part of Sheepsmeadow.

Sheepsmeadow is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Sheepsmeadow is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Sheepsmeadow. If not, see <https://www.gnu.org/licenses/>.
*/

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
