package Model.Entities.Agents.Behavior.Actions;

import Model.Entities.Agents.Agent;
import Model.Neighbourhood.Neighbourhood;
import sim.engine.SimState;

public interface Action {
    
    /**
     * This method encapsulates the behavior, that should be performed by the action.
     * @param agent Agent object, which performs the action
     * @param neighbourhood Object containing information about the neighbourhood
     * @param state Current simulation state. Can be used to schedule or remove something from the schedule.
     */
    public void execute(Agent agent, Neighbourhood neighbourhood, SimState state);

    /**
     * A condition, that has to be fullfilled in order to execute the "Action". Checks if the condition can be applied to at least one cell of "neighbourhood".
     * @param agent Agent object, which performs the action. Can be used to check Conditions based on the agent's state.
     * @param neighbourhood Object containing information about the neighbourhood. Can be used to check Conditions based on the neighbourhood state.
     * @param state Current simulation state. Can be used to check Conditions based on the model state.
     * @return true if condition is fullfilled, else false
     */
    public boolean checkCondition(Agent agent, Neighbourhood neighbourhood, SimState state);
}
