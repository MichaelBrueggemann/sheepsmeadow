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

import Model.Model;
import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;
import Model.Neighbourhood.*;

import sim.engine.SimState;
import sim.engine.Stoppable;

public class ReproduceSheep extends GeneralAction
{
    public ReproduceSheep(int priority)
    {
        super(
            "Reproduce",
            "Create a new agent of the same type as the agent executing this action",
            priority
        );
    }

    @Override
    public void execute(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        if (!(agent instanceof Sheep))
        {
            throw new IllegalArgumentException("The Agent executing this Action has to be of type "+ Sheep.class + ", but is " + agent.getClass() + " instead!");
        }

        Sheep sheep = (Sheep) agent;

        Model model = (Model) state;

        // storage for each type of neighbour
        ArrayList<Cell> sheepCells = new ArrayList<>();
        ArrayList<Cell> grassCells = new ArrayList<>();

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Sheep)
            {
                sheepCells.add(cell);
            }
            else if (cell.getEntity() instanceof Grass)
            {
                grassCells.add(cell);
            }
            // else nothing
        }

       
        // get index of a random cell containing a sheep
        int sheepIndex = sheep.getRng().nextInt(sheepCells.size());

        // get index of a random cell containing grass (free cell)
        int grassIndex = sheep.getRng().nextInt(grassCells.size());

        // pick a random cell containing a sheep
        // could be used to have some information to use in creating a new agent
        Cell agentCell = sheepCells.get(sheepIndex);

        // get mating partner
        Sheep matingPartner = (Sheep) agentCell.getEntity();

        // change mating partners reproducibility status
        matingPartner.setCanReproduceAgain(false);

        // change reproducibility status of the agent performing this action
        sheep.setCanReproduceAgain(false);

        Cell freeGrassCell = grassCells.get(grassIndex);

        // TODO: make energy of new agent dependent on something (Distribution, Energy of parents)

        // reproduce with this sheep by creating a new sheep in a free neighbouring cell
        Sheep newbornSheep = new Sheep(20, sheep.getGrid(), sheep.getRng(), model.getReproductionDelay());

        System.out.println(newbornSheep.getClass().getSimpleName() + ": " + newbornSheep.getId() + " was born!\n");

        try 
        {
            // place new sheep in a free neighbouting cell
            newbornSheep.updateGridLocationTo(freeGrassCell.getLocation().getX(), freeGrassCell.getLocation().getY(), false);

            // add newbornSheep to the schedule
            Stoppable scheduleStopper = state.schedule.scheduleRepeating(newbornSheep);
            newbornSheep.setScheduleStopper(scheduleStopper);

        } 
        catch (Exception e) 
        {
            System.err.println(e.getMessage());
        }
        
    
    }

    @Override
    public boolean checkCondition(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        boolean hasSheep = false;
        boolean hasGrassCell = false;
        boolean hasFertileTargetAgent = false;

        Model model = (Model) state;

        // check if agent is able to reproduce
        boolean canReproduce = agent.getCanReproduceAgain();

        // check if reproduction would be successful
        boolean reproductionSuccessful = (1 - model.getSheepFertilityRate()) < model.random.nextFloat(false, true);

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Sheep)
            {
                hasSheep = true;

                Sheep targetAgent = (Sheep) cell.getEntity();

                // check if any target sheep agent could reproduce this step
                if (targetAgent.getCanReproduceAgain() && targetAgent.isAlive())
                {
                    hasFertileTargetAgent = true;
                }
            }
            else if (cell.getEntity() instanceof Grass)
            {
                hasGrassCell = true;
            }
            // else nothing
        }
        return hasSheep && hasGrassCell && canReproduce && reproductionSuccessful && hasFertileTargetAgent;
    }    
}
