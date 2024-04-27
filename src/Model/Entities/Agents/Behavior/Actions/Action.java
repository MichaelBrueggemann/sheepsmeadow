package Model.Entities.Agents.Behavior.Actions;

import Model.Entities.Agents.Agent;
import Model.Neighbourhood.Cell;

public interface Action {
    
    /**
     * This method encapsulates the behavior, that should be performed by the action.
     * @param agent Agent object, which perform the action
     * @param cell Object containing information about the cell
     */
    public void execute(Agent agent, Cell cell);

    /**
     * A condition, that has to be fullfilled in order to execute the "Action".
     * @param cell Object containing information about the cell
     * @return true if condition is fullfilled, else false
     */
    public boolean checkCondition(Cell cell);
}
