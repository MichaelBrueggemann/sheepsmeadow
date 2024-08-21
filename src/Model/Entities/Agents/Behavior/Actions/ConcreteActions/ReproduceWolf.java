package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import java.util.ArrayList;

import Model.Model;
import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Wolf;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;
import Model.Exceptions.GridLocationOccupiedException;
import Model.Neighbourhood.*;
import sim.engine.SimState;
import sim.engine.Stoppable;

public class ReproduceWolf extends GeneralAction
{
    public ReproduceWolf(int priority)
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
        if (!(agent instanceof Wolf))
        {
            throw new IllegalArgumentException("The Agent executing this Action has to be of type " + Wolf.class + ", but is " + agent.getClass() + " instead!");
        }

        Wolf wolf = (Wolf) agent;

        Model model = (Model) state;

        // storage for each type of neighbour
        ArrayList<Cell> wolfCells = new ArrayList<>();
        ArrayList<Cell> grassCells = new ArrayList<>();

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Wolf) 
            {
                wolfCells.add(cell);
            }
            else if (cell.getEntity() instanceof Grass)
            {
                grassCells.add(cell);
            }
            // else nothing
        }

        // get index of a random cell containing a wolf
        int wolfIndex = wolf.getRng().nextInt(wolfCells.size());

        // get index of a random cell containing grass (free cell)
        int grassIndex = wolf.getRng().nextInt(grassCells.size());

        // pick a random cell containing a wolf
        // could be used to have some information to use in creating a new agent
        Cell agentCell = wolfCells.get(wolfIndex);

        // get mating partner
        Wolf matingPartner = (Wolf) agentCell.getEntity();

        // change mating partners reproducibility status
        matingPartner.setCanReproduceAgain(false);

        // change reproducibility status of the agent performing this action
        wolf.setCanReproduceAgain(false);

        Cell freeGrassCell = grassCells.get(grassIndex);

        // reproduce with this wolf by creating a new wolf in a free neighbouring cell
        Wolf newbornWolf = new Wolf(20, wolf.getGrid(), wolf.getRng(), model.getReproductionDelay());

        System.out.println(newbornWolf.getClass().getSimpleName() + ": " + newbornWolf.getId() + " was born!\n");

        // place new wolf in a free neighbouting cell
        try 
        {
            newbornWolf.updateGridLocationTo(freeGrassCell.getLocation().getX(), freeGrassCell.getLocation().getY(), false);

            // add nwebornWolf to the schedule
            Stoppable scheduleStopper = state.schedule.scheduleRepeating(newbornWolf);
            newbornWolf.setScheduleStopper(scheduleStopper);
        } 
        catch (GridLocationOccupiedException e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkCondition(Agent agent, Neighbourhood neighbourhood, SimState state)
    {
        boolean hasWolf = false;
        boolean hasGrassCell = false;
        boolean hasFertileTargetAgent = false;

        Model model = (Model) state;

        // check if agent is able to reproduce
        boolean canReproduce = agent.getCanReproduceAgain();

        // check if reproduction would be successful
        boolean reproductionSuccessful = (1 - model.getWolfFertilityRate()) < model.random.nextFloat(false, true);

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Wolf)
            {
                hasWolf = true;

                Wolf targetAgent = (Wolf) cell.getEntity();

                // check if any target wolf agent could reproduce this step
                if (targetAgent.getCanReproduceAgain())
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
        return hasWolf && hasGrassCell && canReproduce && reproductionSuccessful && hasFertileTargetAgent;
    }    
}
