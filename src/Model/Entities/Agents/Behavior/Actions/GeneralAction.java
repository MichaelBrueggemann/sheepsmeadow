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

package Model.Entities.Agents.Behavior.Actions;

/**
 * @implNote The interface "Action" will be implemented in the subclasses.
 */
public abstract class GeneralAction implements Action 
{
    
  // priority value used for the PriorityQuene in the agent's "ruleSet"
  private int priority;

  private String name;

  private String description;

  public GeneralAction(String name , String description, int priority)
  {
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  public int getPriority() 
  {
    return this.priority;
  }

  public void setPriority(int value) {
    this.priority = value;
  }

  public String getName() 
  {
    return this.name;
  }

  public void setName(String value) 
  {
    this.name = value;
  }

  public String getDescription() 
  {
    return this.description;
  }

  public void setDescription(String value) 
  {
    this.description = value;
  }
}
