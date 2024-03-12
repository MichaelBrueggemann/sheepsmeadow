package Model.Entities.Agents;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Stack;

import Model.Model;
import Model.Entities.Entity;
import Model.Neighbourhood.Neighbourhood;
import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;

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

    // grid where all agents are stored
    private ObjectGrid2D grid;



    // ===== CONSTRUCTORS =====

    public Agent(int id, Color color, int energy, ObjectGrid2D grid)
    {
        super(id, color);
        this.energy = energy;
        this.location = null;
        this.grid = grid;
    }
    
    // ===== METHODS =====


    public void step(SimState state)
    {
        // access model instance
        Model model = (Model) state;
        
        // access agentGrid
        

        // look at neighbour cells to determine if movement is possible
        //HashMap<Int2D, Agent> neighbours = this.checkNeighbours();
    }

    /**
     * Neighbourhood is defined as all adjacent cells in each main direction. If no neighbour is present, null is inserted instead.
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

        // look above
        top_location = new Int2D(location.getX(), location.getY() - 1);
        stack = (Stack<Entity>) this.grid.get(top_location.getX(), top_location.getY());
        top = stack.peek();
        neighbours[0] = new Neighbourhood(top, top_location);

        // look below
        bottom_location = new Int2D(location.getX(), location.getY() + 1);
        stack = (Stack<Entity>) this.grid.get(bottom_location.getX(), bottom_location.getY() + 1);
        bottom = stack.peek();
        neighbours[1] = new Neighbourhood(bottom, bottom_location);

        // look left
        left_location = new Int2D(location.getX() - 1, location.getY());
        stack = (Stack<Entity>) this.grid.get(left_location.getX() - 1, left_location.getY());
        left = stack.peek();
        neighbours[2] = new Neighbourhood(left, left_location);

        // look right
        right_location = new Int2D(location.getX() + 1, location.getY());
        stack = (Stack<Entity>) this.grid.get(right_location.getX() + 1, right_location.getY());
        right = stack.peek();
        neighbours[3] = new Neighbourhood(right, right_location);


        // return first neighbour (highest precedence)
        return neighbours[0];
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
}
