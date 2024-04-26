package Model.Entities.Agents.Behavior.Actions;

import Model.Neighbourhood.Cell;

/**
 * @implNote The interface "Action" will be implemented in the subclasses.
 */
public abstract class GeneralAction implements Action 
{
    
  // priority value used for the PriorityQuene in the agent's "ruleSet"
  private int priority;

  private String name;

  private String description;

  // class instance for singleton design pattern
  // private static GeneralAction instance;

  public GeneralAction(String name , String description, int priority)
  {
    this.name = name;
    this.description = description;
    this.priority = priority;
  }

  // Singleton factory method, that needs to be implemented by each "concrete action"
  // public abstract GeneralAction getInstance();
  
  // every GeneralAction must implement a function to check, if the GeneralAction can be performed on the entity
  public abstract boolean checkCondition(Cell neighbour);

 
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
