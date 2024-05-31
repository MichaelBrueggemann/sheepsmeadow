package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;

import Model.Neighbourhood.Neighbourhood;
import sim.engine.SimState;


public class LoseEnergy extends GeneralAction
{
    public LoseEnergy(int priority)
    {
        super(
            "LoseEnergy",
            "Reduce Agents energy by 1. Is only used as a fallback, if no other action can be executed!",
            priority
        );
    }

    @Override
    public void execute(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        // reduce agents energy by one.
        agent.setEnergy(agent.getEnergy() - 1);
    }

    @Override
    public boolean checkCondition(Neighbourhood neighbourhood)
    {
        // this condition is always fullfilled as it is used as a fallback, whenn no other condition was fullfilled.
        return true;
    }   
}
