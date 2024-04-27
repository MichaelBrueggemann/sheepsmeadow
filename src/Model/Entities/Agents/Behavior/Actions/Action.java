package Model.Entities.Agents.Behavior.Actions;

import Model.Entities.Agents.Agent;
import Model.Neighbourhood.Neighbourhood;

public interface Action {
    
    /**
     * This method encapsulates the behavior, that should be performed by the action.
     * @param agent Agent object, which perform the action
     * @param neighbourhood Object containing information about the neighbourhood
     */
    public void execute(Agent agent, Neighbourhood neighbourhood);

    /**
     * A condition, that has to be fullfilled in order to execute the "Action". Checks if the condition can be applied to at least one cell of "neighbourhood".
     * @param neighbourhood Object containing information about the neighbourhood
     * @return true if condition is fullfilled, else false
     */
    public boolean checkCondition(Neighbourhood neighbourhood);
}
