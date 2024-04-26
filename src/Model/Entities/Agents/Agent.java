package Model.Entities.Agents;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;

import java.awt.Color;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

import Model.Model;
import Model.Entities.*;
import Model.Entities.Agents.Behavior.Actions.*;
import Model.Exceptions.GridPositionOccupiedException;
import Model.Neighbourhood.Cell;
import Model.Neighbourhood.Neighbourhood;


/**
 * This is a abstract class defining general behavior and attributes of all Agents.
 */
public abstract class Agent extends Entity
{
    // ===== ATTRIBUTES =====

    // determines the "lifetime" of an agent
    protected int energy;

    // location of the agent inside the grid
    protected Int2D location;

    // state of the agent, whether its alive or not (energy = 0)
    protected boolean alive;

    // storage for all "Action"s an agent can perform
    protected PriorityQueue<GeneralAction> ruleSet;

    // can be used to stop the reallocation of the agent to the schedule
    protected Stoppable scheduleStopper;

    // grid where all agents are stored
    protected ObjectGrid2D grid;


    // ===== CONSTRUCTORS =====

    public Agent(int id, Color color, int energy, ObjectGrid2D grid)
    {
        super(id, color);
        this.energy = energy;
        this.location = null; // will be set on model setup
        this.grid = grid;
        this.alive = true;
    }
    
    // ===== METHODS =====


    public void step(SimState state)
    {
        // access model instance
        Model model = (Model) state;
        
        // get the Neighbourhood of this agent
        Neighbourhood neighbourhood = this.checkNeighbours();

        // perform an "Action"
        this.evaluateRuleSet(neighbourhood);

        if (this.energy == 0)
        {
            this.die();
        }
    }

    /**
     * Un-alives the agent. The Agent will then be ultimately removed from the schedule.
     */
    public void die()
    {
        // change state of the agent
        this.alive = false;

        // remove agent from the schedule
        this.scheduleStopper.stop();
    }

    /**
     * Neighbourhood is defined as all adjacent cells in each main direction. If no neighbour is present, null is inserted instead.
     * This function returns one Neighbourhood. This neighbourhood is picked based on the "priorityList" of the current agent instance, so that only a neighbourhood is returned which corresponding Entity is highest on this current Agents "priorityList".
     * @return Neighbourhood with Entity object and location.
     */
    @SuppressWarnings("unchecked")
    public Neighbourhood checkNeighbours()
    {
        // placeholder for the stack in each neighbouring cell
        Stack<Entity> stack;

        Entity top;
        Int2D top_location;
        Entity bottom;
        Int2D bottom_location;
        Entity left;
        Int2D left_location;
        Entity right;
        Int2D right_location;

        // query each direction
        Cell topNeighbour;
        try 
        {
            // look above
            top_location = new Int2D(location.getX(), location.getY() - 1);
            stack = (Stack<Entity>) this.grid.get(top_location.getX(), top_location.getY());
            top = stack.peek();
            topNeighbour = new Cell(top, top_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No bottom neighbour found!");
            topNeighbour = null;
        }

        Cell bottomNeighbour;
        try 
        {
            // look below
            bottom_location = new Int2D(location.getX(), location.getY() + 1);
            stack = (Stack<Entity>) this.grid.get(bottom_location.getX(), bottom_location.getY());
            bottom = stack.peek();
            bottomNeighbour = new Cell(bottom, bottom_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No bottom neighbour found!");
            bottomNeighbour = null;
        }

        Cell leftNeighbour;
        try 
        {
            /// look left
            left_location = new Int2D(location.getX() - 1, location.getY());
            stack = (Stack<Entity>) this.grid.get(left_location.getX(), left_location.getY());
            left = stack.peek();
            leftNeighbour = new Cell(left, left_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No left neighbour found!");
            leftNeighbour = null;
        }

        Cell rightNeighbour;
        try 
        {
            // look right
            right_location = new Int2D(location.getX() + 1, location.getY());
            stack = (Stack<Entity>) this.grid.get(right_location.getX(), right_location.getY());
            right = stack.peek();
            rightNeighbour = new Cell(right, right_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No right neighbour found!");
            rightNeighbour = null;
        }
        
        // add all Neighbours to a Neighbourhood
        Neighbourhood neighbourhood = new Neighbourhood(topNeighbour, rightNeighbour, bottomNeighbour, leftNeighbour);

        System.out.println("\n");
        
        int entries = 0;
        int i = 1;
        // for debugging
        for (Cell n : neighbourhood.getAllNeighbours())
        {
            
            if (n != null) 
            {
                System.out.println("Neighbour: " + n.getEntity());
                System.out.println("ID: " + n.getEntity().getId());
                System.out.println("Position: " + i);
            }
            else
            {
                System.out.println("Neighbour: null");
                System.out.println("Position: " + i);
            }
            i++;
            entries++;
        }

        System.out.println("Current Agent: " + this + "\n");

        System.out.println("Entries in neighbours: " + entries + " \n");
            
        return neighbourhood;
    }

    /**
     * Evaluates, if any action of this agents "ruleSet" can be executed on any element of "neighbourhood". If an Action can be executed, this Action will be executed. Otherwise nothing happens.
     * @param neighbourhood Collection of neighbouring cells
     */
    public void evaluateRuleSet(Neighbourhood neighbourhood)
    {
        // create iterator
        Iterator<GeneralAction> ruleSetIterator = this.ruleSet.iterator();

        boolean actionExecuted = false;

        while (ruleSetIterator.hasNext())
        {
            GeneralAction action = ruleSetIterator.next();

            System.out.println("Trying action...");
            System.out.println("Action Name: " + action.getName());
            System.out.println("Action Description: " + action.getDescription());

            // check if "action" can be executed on any of the neighbours
            for (Cell neighbourCell : neighbourhood.getAllNeighbours())
            {
                // only evaluate Actions of the ruleSet if there is a Neighbour
                if (neighbourCell == null)
                {
                    continue;
                }
                else
                {
                    if (action.checkCondition(neighbourCell))
                    {
                        System.out.println("Condition was fullfilled!");
                        // execute the action
                        System.out.println("execute " + action.getName() + "...");
                        action.execute(this, neighbourCell);
                        // change flag, to stop looping through the iterator
                        actionExecuted = true;
                        break;
                    }
                    // else do nothing
                }
            }
            
            if (actionExecuted) break;
        }
    }


    /**
     * Utility function.
     * Pushes this agent on the given stack and updates it's "location" based on the given "x" and "y" values.
     * @param cell Stack object representing the cell where the agent will be placed
     * @param x position of the agent on the x axis of the grid
     * @param y position of the agent on the y axis of the grid
     */
    private void addToLocation(Stack<Entity> cell, int x, int y)
    {
        // push entity on the stack
        cell.push(this);

        // update location of the agent
        this.setLocation(new Int2D(x,y));
    }

    /**
     * Check if a position on the grid is already occupied. If yes, dont perform a move, else move the agent to the new location.
     * The agent will also be removed from it's current position.
     * 
     * @implNote The grid cell has to be free beforehand. This methods checks, if the grid position is occupied, but it doesn't free it.
     * 
     * @param x new x position
     * @param y new y position
     * @throws GridPositionOccupiedException 
     */
    @SuppressWarnings("unchecked")
    public void updateGridPosition(int x, int y) throws GridPositionOccupiedException
    {
        // fetch the stack for the x,y coordinates
        Stack<Entity> new_cell = (Stack<Entity>) this.grid.get(x,y);

        Int2D old_location = this.location;

        // check state of the stack (new position)
        if (new_cell.size() >= 2)
        {
            throw new GridPositionOccupiedException("The location at x: " + x + ", y: " + y + " is already occupied!");
        }
        else if (new_cell.size() == 1)
        {
            this.addToLocation(new_cell, x, y);
            System.out.println("Position of '" + this.getClass().getSimpleName() + ": " + this.getId() + "' successfully updated to x: " + x + ", y: " + y + "!");

            // remove agent from it's old location
            Model.emptyGridCell(this.grid, old_location.getX(), old_location.getY());
            System.out.println("Sucessfully removed '" + this.getClass().getSimpleName() + ": " + this.getId() + "' from position x: " + old_location.getX() + ", y: " + old_location.getY() + ".");
        }
        else
        {
            throw new IllegalAccessError("Error in allocating a new Position.");
        }
        
    }

    // ===== GETTER & SETTER =====

    public Int2D getLocation() 
    {
        return this.location;
    }

    public void setLocation(Int2D location) throws IllegalArgumentException
    {

        // check if new location is in bounds of the grid
        if (
            (location.getX() < 0 || location.getX() > this.grid.getWidth()) || 
            (location.getY() < 0 || location.getY() > this.grid.getHeight())
        )
        {
            throw new IllegalArgumentException("Location is out of bound!");
        }

        this.location = location;
    }

    public ObjectGrid2D getGrid() 
    {
       return this.grid;
    }
   
    public int getEnergy()
    {
        return this.energy;
    }

    public void setEnergy(int value) throws IllegalArgumentException
    {
        if (value < 0) throw new IllegalArgumentException("New Energy can't be negative!");

        this.energy = value;
    }

    public Stoppable getScheduleStopper() 
    {
      return this.scheduleStopper;
    }

    public void setScheduleStopper(Stoppable value) 
    {
      this.scheduleStopper = value;
    }
}
