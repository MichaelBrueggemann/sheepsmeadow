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
import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Wolf;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Neighbourhood.*;

import sim.engine.SimState;

public class EatSheep extends GeneralAction
{
    public EatSheep(int priority)
    {
        super(
            "EatSheep", 
            "Eat a Sheep Agent in any neighbouring cell and move the agent to this cell. Increase the Wolfs energy by 20.", 
            priority);
    }    

    @Override
    public void execute(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        if (!(agent instanceof Wolf))
        {
            throw new IllegalArgumentException("The Agent executing this Action has to be of type " + Wolf.class + ", but is " + agent.getClass() + " instead!");
        }

        // cast agent to its actual type
        Wolf wolf = (Wolf) agent;

        // find all neighbouring Cells that contain a Sheep-object 
        ArrayList<Cell> sheepCells = new ArrayList<>();

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Sheep)
            {
                Sheep sheep = (Sheep) cell.getEntity();

                // only sheeps that are alive should be considered for eating
                if (sheep.isAlive())
                {
                    sheepCells.add(cell);
                }
            }
            // else nothing
        }

        // get index of a random cell containing a sheep
        int index = agent.getRng().nextInt(sheepCells.size());

        // pick a random cell containing a sheep
        Cell sheepCell = sheepCells.get(index);

        Sheep sheep = (Sheep) sheepCell.getEntity();

        try 
        {
            // remove sheep from the schedule
            sheep.die();

            // place the grass cell assigned to the sheep back on the grid & remove sheep agent from it's grid location
            sheep.getGrid().set(sheepCell.getLocation().getX(), sheepCell.getLocation().getY(), sheep.getGrasscell());

            // place wolf in the cell of the sheep
            wolf.updateGridLocationTo(sheepCell.getLocation().getX(), sheepCell.getLocation().getY(), true);

            //TODO: check if sheep objects could be marked for garbage collection to free up memory

            System.out.println(sheep.getClass().getSimpleName() + ": " + sheep.getId() + " was eaten!\n");

            // increase energy by 20
            wolf.setEnergy(wolf.getEnergy() + 20);
        } 
        catch (Exception e) 
        {

        }
    }

    @Override
    public boolean checkCondition(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        boolean conditionFullfilled = false;

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Sheep)
            {
                Sheep sheep = (Sheep) cell.getEntity();

                if (sheep.isAlive())
                {
                    conditionFullfilled = true;
                }
            }
            // else nothing
        }
        return conditionFullfilled;
    }
}
