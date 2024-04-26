package Model.Neighbourhood;


/**
 * Represents a neighbourhood relation in a grid. Neighbourhood is defined as all adjacent cells in each main direction.
 */
public class Neighbourhood {
    
  // neighbouring agents and their location
  private Neighbour top;
  private Neighbour rigth;
  private Neighbour bottom;
  private Neighbour left;


  public Neighbourhood(Neighbour top, Neighbour rigth, Neighbour bottom, Neighbour left)
  {
    this.top = top;
    this.rigth = rigth;
    this.bottom = bottom;
    this.left = left;
  }
  
  public Neighbour getTop() 
  {
    return this.top;
  }

  public Neighbour getRight() 
  {
    return this.rigth;
  }

  public Neighbour getBottom() 
  {
    return this.bottom;
  }

  public Neighbour getLeft() 
  {
    return this.left;
  }

  public Neighbour[] getAllNeighbours()
  {
    Neighbour[] allNeighbours = {this.top, this.rigth, this.bottom, this.left};
    return allNeighbours;
  }

  
  
}
