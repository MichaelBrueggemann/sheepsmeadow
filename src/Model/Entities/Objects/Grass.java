package Model.Entities.Objects;

import java.awt.Color;

import Model.Entities.Entity;
import ec.util.MersenneTwisterFast;
import sim.engine.SimState;

public class Grass extends Entity
{
    // time span until grass is "edible" again
    private int regrothPeriod = 3;

    private int regrothCounter = regrothPeriod;

    // status flag, if grass was "eaten" by a sheep
    private boolean isRegrowing;

    public Grass(int id, MersenneTwisterFast rng)
    {
        super(id, Color.green, rng);
        this.isRegrowing = false;
    }

    @Override
    public void step(SimState state)
    {

    }

    public boolean getIsRegrowing() 
    {
      return this.isRegrowing;
    }

    public void setRegrowing(boolean value) 
    {
      this.isRegrowing = value;
    }

    public int getRegrothPeriod() 
    {
      return this.regrothPeriod;
    }

    public void setRegrothPeriod(int value) 
    {
      this.regrothPeriod = value;
    }

    public int getRegrothCounter() 
    {
      return this.regrothCounter;
    }
    
    public void setRegrothCounter(int value) 
    {
      this.regrothCounter = value;
    }
}
