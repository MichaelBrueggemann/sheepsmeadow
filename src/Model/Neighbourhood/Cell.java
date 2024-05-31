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
