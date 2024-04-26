package Model.Entities.Agents.Behavior.Actions;

import Model.Entities.Agents.Agent;
import Model.Neighbourhood.Cell;

public interface Action {
    
    /**
     * This method encapsulates the behavior, that should be performed by the action.
     */
    public void execute(Agent agent, Cell neighbour);
}
