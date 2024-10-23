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


/**
 * Represents a neighbourhood relation in a grid. Neighbourhood is defined as all adjacent cells in each main direction.
 */
public class Neighbourhood 
{
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

  public String toString()
  {
    // add entity to string. if null, then write "null" into the string
    return "[ Top: " + ((this.top != null) ? this.top.getEntity() : "null") + ", Right: " + ((this.rigth != null) ? this.rigth.getEntity() : "null") + ", Bottom: " + ((this.bottom != null) ? this.bottom.getEntity() : "null") + ", Left: " + ((this.left != null) ? this.left.getEntity() : "null") + " ]";
  }
}
