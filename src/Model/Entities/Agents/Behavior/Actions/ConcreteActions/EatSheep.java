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
                sheepCells.add(cell);
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
            sheep.getScheduleStopper().stop();

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
                conditionFullfilled = true;
            }
            // else nothing
        }
        return conditionFullfilled;
    }
}
