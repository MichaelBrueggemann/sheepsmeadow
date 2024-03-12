package Model.Neighbourhood;

import Model.Entities.Entity;
import sim.util.Int2D;

/**
 * Represents a neighbourhood relation in a grid. Neighbourhood is defined as all adjacent cells in each main direction.
 */
public class Neighbourhood {
    
    // neighbour agent
    private Entity neighbour;

    // location of the neighbouring entity
    private Int2D neighbourLocation;

    public Neighbourhood(Entity neighbour, Int2D location)
    {
        this.neighbour = neighbour;
        this.neighbourLocation = location;
    }
    

    public Entity getNeighbour() {
      return this.neighbour;
    }
    public void setNeighbour(Entity value) {
      this.neighbour = value;
    }

    public Int2D getneighbourLocation() {
      return this.neighbourLocation;
    }
    public void setneighbourLocation(Int2D value) {
      this.neighbourLocation = value;
    }
}
