package Model.Neighbourhood;


/**
 * Represents a neighbourhood relation in a grid. Neighbourhood is defined as all adjacent cells in each main direction.
 */
public class Neighbourhood {
    
  // neighbouring agents and their location
  private Cell top;
  private Cell rigth;
  private Cell bottom;
  private Cell left;


  public Neighbourhood(Cell top, Cell rigth, Cell bottom, Cell left)
  {
    this.top = top;
    this.rigth = rigth;
    this.bottom = bottom;
    this.left = left;
  }
  
  public Cell getTop() 
  {
    return this.top;
  }

  public Cell getRight() 
  {
    return this.rigth;
  }

  public Cell getBottom() 
  {
    return this.bottom;
  }

  public Cell getLeft() 
  {
    return this.left;
  }

  public Cell[] getAllNeighbours()
  {
    Cell[] allNeighbours = {this.top, this.rigth, this.bottom, this.left};
    return allNeighbours;
  }

  
  
}
