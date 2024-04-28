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
