package Model.Entities.Agents;

import java.awt.Color;
import java.util.HashMap;

import Model.Model;
import Model.Entities.Entity;

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

    // grid where the agent is placed
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
        
        // access grid
        ObjectGrid2D grid = model.getMeadow();

        // look at neighbour cells to determine if movement is possible
        HashMap<Int2D, Agent> neighbours = this.checkNeighbours();
    }

    /**
     * Neighbourhood is defined as all adjacent cells in each main direction. All neighbours will be stored in a
     * HashMap. If no neighbour is present, null is inserted instead.
     * @return HashMap with the neighbours location as key and the neighbour object as value
     */
    public HashMap<Int2D, Agent> checkNeighbours()
    {
        HashMap<Int2D, Agent> neighbours = new HashMap<Int2D, Agent>();
        Agent top;
        Agent bottom;
        Agent left;
        Agent right;

        // query each direction
        
        // look above
        top = (Agent) this.grid.get(location.getX(), location.getY() - 1);

        // look below
        bottom = (Agent) this.grid.get(location.getX(), location.getY() + 1);

        // look left
        left = (Agent) this.grid.get(location.getX() - 1, location.getY());

        // look right
        right = (Agent) this.grid.get(location.getX() + 1, location.getY());


        return neighbours;
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
