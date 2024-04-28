package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import java.util.ArrayList;

import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;
import Model.Neighbourhood.Cell;
import Model.Neighbourhood.Neighbourhood;
import sim.engine.SimState;

public class EatGrass extends GeneralAction
{
    /**
     * Move the Agent to a free neighbouring cell and reduce it's energy by 1
     */
    public EatGrass(int priority)
    {
        super(
            "EatGrass",
            "Eat a not already eaten Grass cell and move the Sheep to this cell.", 
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

        if (!(agent instanceof Sheep))
        {
            throw new IllegalArgumentException("The Agent executing this Action has to be of type " + Sheep.class + ", but is " + agent.getClass() + " instead!");
        }

        // cast agent to its actual tyoe
        Sheep sheep = (Sheep) agent;

        // find all Cells that contain a Grass-object
        ArrayList<Cell> grassCells = new ArrayList<>();

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Grass)
            {
                grassCells.add(cell);
            }
            // elso nothing
        }

        // get index of a random grass-cell
        int index = agent.getRng().nextInt(grassCells.size());

        // pick a random grass cell
        Cell grassCell = grassCells.get(index);


        try 
        {
            // change status of grassCell to "regrowing"
            Grass grass = (Grass) grassCell.getEntity();
            grass.setRegrowing(true);

            // increase the sheeps energy by 4
            sheep.setEnergy(sheep.getEnergy() + 4);

            // move agent to new cell
            sheep.updateGridLocationTo(grassCell.getLocation().getX(), grassCell.getLocation().getY(), true);
        } 
        catch (Exception e) 
        {
            // GridPositionOccupiedException can't occur, as the condition checks that the cell has to be grass (therefore is free)
        }
    }


    @Override
    /**
     * Checks if this Action can be executed on the Neighbourhood.
     */
    public boolean checkCondition(Neighbourhood neighbourhood) 
    {
        boolean conditionFullfilled = false;

        for (Cell cell : neighbourhood.getAllNeighbours())
        {
            if (cell == null)
            {
                continue;
            }
            else if (cell.getEntity() instanceof Grass && !((Grass) cell.getEntity()).getIsRegrowing())
            {
                conditionFullfilled = true;
            }
            // elso nothing
        }
        
        return conditionFullfilled;
    }
}
