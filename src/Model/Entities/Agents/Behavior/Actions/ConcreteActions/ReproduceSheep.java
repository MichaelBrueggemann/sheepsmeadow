package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import java.util.ArrayList;

import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Wolf;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;
import Model.Neighbourhood.*;
import sim.engine.SimState;

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

        Cell grassCell = grassCells.get(grassIndex);

        // TODO: make energy of new agent dependent on something (Distribution, Energy of parents)

        // reproduce with this wolf by creating a new wolf in a free neighbouring cell
        Sheep newbornSheep = new Sheep(1, 20, sheep.getGrid(), sheep.getRng());

        // place new wolf in a free neighbouting cell
        newbornSheep.updateGridLocationTo(grassCell.getLocation().getX(), grassCell.getLocation().getY(), false);
    
    }

    @Override
    public boolean checkCondition(Neighbourhood neighbourhood)
    {
        boolean hasSheep = false;
        boolean hasGrassCell = false;

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Sheep)
            {
                hasSheep = true;
                break;
            }
            else if (cell.getEntity() instanceof Grass)
            {
                hasGrassCell = true;
                break;
            }
            // else nothing
        }
        return hasSheep && hasGrassCell;
    }    
}
