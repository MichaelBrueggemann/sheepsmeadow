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

import java.util.ArrayList;

import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;
import Model.Neighbourhood.Cell;
import Model.Neighbourhood.Neighbourhood;
import sim.engine.SimState;


public class DefaultMove extends GeneralAction
{

    /**
     * Move the Agent to a free neighbouring cell and reduce it's energy by 1
     */
    public DefaultMove(int priority)
    {
        super(
            "DefaultMove",
            "Move the Agent to a free neighbouring cell and reduce it's energy by 1", 
            priority
        );
    }
    

    @Override
    /**
     * Applies this GeneralAction for a given Agent and a neighbouring cell.
     * @param agent Agent, who perform this GeneralAction
     * @param cell neighbouring cell, on which the this GeneralAction should be executed
     */
    public void execute(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        // find all Cells that contain a Grass-object
        ArrayList<Cell> grass = new ArrayList<>();

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Grass)
            {
                grass.add(cell);
            }
            // elso nothing
        }

        // get index of a random grass-cell
        int index = agent.getRng().nextInt(grass.size());

        // pick a random grass cell
        Cell grassCell = grass.get(index);


        try 
        {
            // move agent to new cell
            agent.updateGridLocationTo(grassCell.getLocation().getX(), grassCell.getLocation().getY(), true);
        } 
        catch (Exception e) 
        {
            // GridPositionOccupiedException can't occur, as the condition checks that the cell has to be grass (therefore is free)
        }

        // reduce energy of agent by 1
        agent.setEnergy(agent.getEnergy() - 1);
    }

    @Override
    /**
     * Checks if this Action can be executed on the Neighbourhood.
     */
    public boolean checkCondition(Agent agent, Neighbourhood neighbourhood, SimState state) 
    {
        boolean conditionFullfilled = false;

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Grass)
            {
                conditionFullfilled = true;
            }
            // else nothing
        }
        
        return conditionFullfilled;
    }


    
}


    