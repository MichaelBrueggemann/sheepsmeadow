package Model.Entities.Agents.Behavior.Actions;

import Model.Entities.Agents.Agent;
import Model.Entities.Objects.Grass;
import Model.Neighbourhood.Cell;


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
    public void execute(Agent agent, Cell cell)
    {
        // reduce energy of agent by 1
        agent.setEnergy(agent.getEnergy() - 1);

        try 
        {
            // move agent to new cell
            agent.updateGridPosition(cell.getLocation().getX(), cell.getLocation().getY());
        } 
        catch (Exception e) 
        {
            // GridPositionOccupiedException can't occur, as the condition checks that the cell has to be grass (therefore is free)
        }
        
    }


    @Override
    /**
     * Checks if this Action can be executed on the cell.
     */
    public boolean checkCondition(Cell cell) 
    {
        if (cell.getEntity() instanceof Grass)
        {
            return true;
        }
        else
        {
            return false;
        }
        
    }


    
}


    