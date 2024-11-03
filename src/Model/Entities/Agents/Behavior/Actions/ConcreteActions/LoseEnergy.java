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
    public boolean checkCondition(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        // this condition is always fullfilled as it is used as a fallback, whenn no other condition was fullfilled.
        return true;
    }   
}
