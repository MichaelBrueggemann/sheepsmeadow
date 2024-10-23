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

package Model.Neighbourhood;

import Model.Entities.Entity;
import sim.util.Int2D;

/**
 * A Cell is a struct of a Entity and it's location.
 */
public class Cell 
{
  // neighbour agent
  private Entity entity;

  // location of the neighbouring entity
  private Int2D location;

  public Cell(Entity entity, Int2D location)
  {
    this.entity = entity;
    this.location = location;
  }
  

  public Entity getEntity() 
  {
    return this.entity;
  }

  public void setEntity(Entity value) 
  {
    this.entity = value;
  }

  public Int2D getLocation() 
  {
    return this.location;
  }

  public void setLocation(Int2D value) {
    this.location = value;
  }
}
