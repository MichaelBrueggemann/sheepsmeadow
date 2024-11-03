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

package Model.Entities.Objects;

import java.awt.Color;

import Model.Entities.Entity;
import ec.util.MersenneTwisterFast;

import sim.engine.SimState;

public class Grass extends Entity
{
  // time span until grass is "edible" again
  private final int regrowthPeriod = 3;

  private int regrowthCounter = regrowthPeriod;

  // status flag, if grass was "eaten" by a sheep
  private boolean isRegrowing;

  public Grass(MersenneTwisterFast rng)
  {
    super(Color.green, rng);
    this.isRegrowing = false;
  }

  @Override
  public void step(SimState state)
  {
    // we don't have to check if the grass is regrowing, as it can only be stepped, when it was eaten by a Sheep
    this.updateRegrowthCounter();

    if (regrowthCounter == 0)
    {
      this.resetRegrowthCounter();

      // remove grass from the schedule
      this.scheduleStopper.stop();
    }

    
  }

  /**
   * Decreament the regrowthCounter.
   */
  private void updateRegrowthCounter()
  {
    if (this.regrowthCounter > 0)
    {
      this.regrowthCounter -= 1;
    }
  }

  public void resetRegrowthCounter()
  {
    this.regrowthCounter = regrowthPeriod;
    this.isRegrowing = false;
  }

  public boolean getIsRegrowing() 
  {
    return this.isRegrowing;
  }

  public void setRegrowing(boolean value) 
  {
    this.isRegrowing = value;
  }

  public int getRegrowthPeriod() 
  {
    return this.regrowthPeriod;
  }

  public int getRegrowthCounter() 
  {
    return this.regrowthCounter;
  }
  
  public void setRegrowthCounter(int value) 
  {
    this.regrowthCounter = value;
  }
}
