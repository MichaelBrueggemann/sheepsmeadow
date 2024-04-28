package Model.Entities.Agents;

import sim.engine.SimState;
import sim.engine.Stoppable;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;
import ec.util.MersenneTwisterFast;

import java.awt.Color;

import java.util.Iterator;
import java.util.PriorityQueue;

import Model.Entities.*;
import Model.Entities.Agents.Behavior.Actions.*;
import Model.Entities.Objects.Grass;
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
    protected PriorityQueue<GeneralAction> ruleset;

    // can be used to stop the reallocation of the agent to the schedule
    protected Stoppable scheduleStopper;

    // grid where all agents are stored
    protected ObjectGrid2D grid;

    // Grass object at the location this agent is currently placed on the grid
    protected Grass grasscell;


    // ===== CONSTRUCTORS =====

    public Agent(int id, Color color, int energy, ObjectGrid2D grid, MersenneTwisterFast rng)
    {
        super(id, color, rng);
        this.energy = energy;
        this.location = null; // will be set on model setup
        this.grid = grid;
        this.alive = true;
    }
    
    // ===== METHODS =====


    public void step(SimState state)
    {        
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

        System.out.println("Agent: '" + this.getClass().getSimpleName() + ": " + this.getId() + "' energy is " + this.getEnergy() + ". Agent has died!");

        // remove agent from the schedule
        this.scheduleStopper.stop();
    }

    /**
     * Neighbourhood is defined as all adjacent cells in each main direction. If no neighbour is present, null is inserted instead.
     * This function returns one Neighbourhood. This neighbourhood is picked based on the "priorityList" of the current agent instance, so that only a neighbourhood is returned which corresponding Entity is highest on this current Agents "priorityList".
     * @return Neighbourhood with Entity object and location.
     */
    public Neighbourhood checkNeighbours()
    {
        // placeholder for the entity in each neighbouring cell
        Entity neighbour;

        Int2D top_location;
        Int2D bottom_location;
        Int2D left_location;
        Int2D right_location;

        // query each direction
        Cell topNeighbour;
        try 
        {
            // look above
            top_location = new Int2D(this.location.getX(), this.location.getY() - 1);
            neighbour = (Entity) this.grid.get(top_location.getX(), top_location.getY());
            topNeighbour = new Cell(neighbour, top_location);
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
            neighbour = (Entity) this.grid.get(bottom_location.getX(), bottom_location.getY());
            bottomNeighbour = new Cell(neighbour, bottom_location);
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
            neighbour = (Entity) this.grid.get(left_location.getX(), left_location.getY());
            leftNeighbour = new Cell(neighbour, left_location);
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
            neighbour = (Entity) this.grid.get(right_location.getX(), right_location.getY());
            rightNeighbour = new Cell(neighbour, right_location);
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
                System.out.println("Neighbour: " + n.getEntity().getClass().getSimpleName() + "@" + System.identityHashCode(n.getEntity().getClass()));
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

        System.out.println("Current Agent: " + this.getClass().getSimpleName() + "@" + System.identityHashCode(this.getClass()) + "\n");

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
        Iterator<GeneralAction> ruleSetIterator = this.ruleset.iterator();

        boolean actionExecuted = false;

        while (ruleSetIterator.hasNext())
        {
            GeneralAction action = ruleSetIterator.next();

            System.out.println("Trying action...");
            System.out.println("Action Name: " + action.getName());
            System.out.println("Action Description: " + action.getDescription());

            // check if "action" can be executed on any of the neighbours
            if (action.checkCondition(neighbourhood))
            {
                System.out.println("\nCondition was fullfilled!");

                // execute the action
                System.out.println("execute " + action.getName() + "...");
                action.execute(this, neighbourhood);

                // change flag, to stop looping through the iterator
                actionExecuted = true;
            }

            if (actionExecuted) break;
        }
    }


    
    /**
     * Utility function.
     * Places this agent on the given grid and updates it's "location" based on the given "x" and "y" values.
     * @param grid grid where the agent will be placed
     * @param x position of the agent on the x axis of the grid
     * @param y position of the agent on the y axis of the grid
     */
    @Override
    public void addToLocation(ObjectGrid2D grid, int x, int y)
    {
        // place agent on the grid
        super.addToLocation(grid, x, y);

        // update location of the agent
        this.setLocation(new Int2D(x,y));
    }

    /**
     * Replace an entity placed at "x", "y" on the grid with this agent. When this Agent has a location on the grid associated to this agent, it will be removed from this location beforehand.
     * 
     * @param x new x position
     * @param y new y position
     * @param hasOldLocation flag to indicate whether this agent already is placed on the grid
     */
    public void updateGridLocationTo(int x, int y, boolean hasOldLocation)
    {
        if (hasOldLocation)
        {
            // save old location of this agent
            Int2D oldLocation = this.location;
                    
            // get grass associated to this agent
            Grass grass = this.grasscell;

            // remove agent from it's old location by overwriting it with it's associated grasscell at the old location
            grass.addToLocation(this.grid, oldLocation.getX(), oldLocation.getY());
            System.out.println("Sucessfully removed '" + this.getClass().getSimpleName() + ": " + this.getId() + "' from position x: " + oldLocation.getX() + ", y: " + oldLocation.getY() + ".");
        }

        // fetch the entity at x,y in the grid
        Entity entity = (Entity) this.grid.get(x,y);

        // store the Grass-Object from the new location to later place it back on the grid, when this agent leaves the cell at x,y
        if (entity instanceof Grass)
        {
            this.grasscell = (Grass) entity;
        }

        // remove Entity currently placed at x,y (by overwriting it with this agent)
        this.addToLocation(this.grid, x, y);
        System.out.println("Position of '" + this.getClass().getSimpleName() + ": " + this.getId() + "' successfully updated to x: " + x + ", y: " + y + "!");
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
