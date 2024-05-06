package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import java.util.ArrayList;

import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Wolf;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;
import Model.Neighbourhood.*;
import sim.engine.SimState;

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
        if (!(agent instanceof Wolf || agent instanceof Sheep))
        {
            throw new IllegalArgumentException("The Agent executing this Action has to be of type " + Wolf.class + ", but is " + agent.getClass() + " instead!");
        }

        Wolf wolf = (Wolf) agent;

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

        Cell grassCell = grassCells.get(grassIndex);

        // reproduce with this wolf by creating a new wolf in a free neighbouring cell
        Wolf newbornWolf = new Wolf(1, 20, wolf.getGrid(), wolf.getRng());

        // place new wolf in a free neighbouting cell
        newbornWolf.updateGridLocationTo(grassCell.getLocation().getX(), grassCell.getLocation().getY(), false);
    }

    @Override
    public boolean checkCondition(Neighbourhood neighbourhood)
    {
        boolean hasWolf = false;
        boolean hasGrassCell = false;

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Wolf)
            {
                hasWolf = true;
            }
            else if (cell.getEntity() instanceof Grass)
            {
                hasGrassCell = true;
            }
            // else nothing
        }
        return hasWolf && hasGrassCell;
    }    
}
