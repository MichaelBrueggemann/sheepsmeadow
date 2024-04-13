package Model.Entities.Agents;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;

import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import Model.Model;
import Model.Entities.Entity;
import Model.Exceptions.GridPositionOccupiedException;
import Model.Neighbourhood.Neighbourhood;


/**
 * This is a abstract class defining general behavior and attributes of all Agents.
 */
public abstract class Agent extends Entity
{
    // ===== ATTRIBUTES =====

    // determines the "lifetime" of an agent
    private int energy;

    // location of the agent inside the grid
    private Int2D location;

    // state of the agent, whether its alive or not (energy = 0)
    private boolean alive;

    // can be used to stop the reallocation of the agent to the schedule
    private Stoppable scheduleStopper;

    // grid where all agents are stored
    private ObjectGrid2D grid;

    // This List defines which type of Entity in a neighbouring cell should be prioritized, when performing an action in a step. It's always based on the Agent class.
    private ArrayList<Object> priorityList = new ArrayList<>();



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
        
        // access agentGrid
        

        // look at neighbour cells to determine if movement is possible
        //HashMap<Int2D, Agent> neighbours = this.checkNeighbours();

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
        Neighbourhood[] neighbours = new Neighbourhood[4];

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

        try 
        {
            // look above
            top_location = new Int2D(location.getX(), location.getY() - 1);
            stack = (Stack<Entity>) this.grid.get(top_location.getX(), top_location.getY());
            top = stack.peek();
            neighbours[0] = new Neighbourhood(top, top_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No top neighbour found!");
        }


        try 
        {
            // look below
            bottom_location = new Int2D(location.getX(), location.getY() + 1);
            stack = (Stack<Entity>) this.grid.get(bottom_location.getX(), bottom_location.getY());
            bottom = stack.peek();
            neighbours[1] = new Neighbourhood(bottom, bottom_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No bottom neighbour found!");
        }

        try 
        {
            /// look left
            left_location = new Int2D(location.getX() - 1, location.getY());
            stack = (Stack<Entity>) this.grid.get(left_location.getX(), left_location.getY());
            left = stack.peek();
            neighbours[2] = new Neighbourhood(left, left_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No left neighbour found!");
        }

        try 
        {
            // look right
            right_location = new Int2D(location.getX() + 1, location.getY());
            stack = (Stack<Entity>) this.grid.get(right_location.getX(), right_location.getY());
            right = stack.peek();
            neighbours[3] = new Neighbourhood(right, right_location);
            System.out.println("Neighbour sucessfully found!");
        } 
        catch (Exception e) 
        {
            System.err.println(e);
            System.err.println("No right neighbour found!");
        }
        
        System.out.println("\n");
        
        int entries = 0;
        // for debugging
        for (Neighbourhood n : neighbours)
        {
            if (n != null) 
            {
                System.out.println("Neighbour: " + n.getNeighbour());
                System.out.println("ID: " + n.getNeighbour().getId() + "\n");
            }
            else
            {
                System.out.println("Neighbour: null\n");
            }
            entries++;
        }

        System.out.println("Current Agent: " + this + "\n");

        System.out.println("Entries in neighbours: " + entries + " \n");
            
        // find the Neighbourhood that contains an Entity which has the highest priority based on this Agents priorityList.
        // 0 = highest priority, from here ascending (1,2,3,...)
        HashMap<Integer, Neighbourhood> priorityMap = new HashMap<>();

        for (int i = 0; i < 4; i++)
        {
            // get neighbourhood
            Neighbourhood neighbourhood = neighbours[i];

            // get priority value of each Neighbour
            if (neighbourhood != null)
            {
                // get neighbour
                Entity neighbour = neighbourhood.getNeighbour();

                // get priority score
                int neighbourPriority = this.priorityList.indexOf(neighbour.getClass());
                System.out.println("Neighbour " + neighbour + " with priority: " + neighbourPriority);
            
                // Add neighbour to priority map
                priorityMap.put(neighbourPriority, neighbourhood);
            }
            // is the neighbourhood "null", we won't have to include it in the priorityMap, as there is no neighbour present
        }

        // iterate over each entry of the hashmap and find the entry with the smallest key (highest priority neighbouring entity)
        Iterator<Map.Entry<Integer, Neighbourhood>> iterator = priorityMap.entrySet().iterator();
        Map.Entry<Integer, Neighbourhood> firstEntry = iterator.next();

        for (Map.Entry<Integer, Neighbourhood> entry : priorityMap.entrySet()) {
            if (entry.getKey() < firstEntry.getKey()) {
                firstEntry = entry;
            }
        }

        System.out.println("\nFirst entry with the lowest key: " + firstEntry);


        // return first neighbourhood (highest priority)
        return firstEntry.getValue();
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
        Stack<Entity> stack = (Stack<Entity>) this.grid.get(x,y);

        // check state of the stack
        if (stack.size() >= 2)
        {
            throw new GridPositionOccupiedException("The location at x: " + x + ", y: " + y + " is already occupied!");
        }
        else if (stack.size() == 1)
        {
            this.addToLocation(stack, x, y);
            System.out.println("Position at: " + x + ", " + y + " successfully updated!");
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

    public ArrayList<Object> getPriorityList() 
    {
      return this.priorityList;
    }

    public void setPriorityList(ArrayList<Object> value) 
    {
      this.priorityList = value;
    }


    public void addPriorityClass(int index, Object priorityClass)
    {
        if (!this.priorityList.contains(priorityClass))
        {
            this.priorityList.add(index, priorityClass);
        }
        
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
