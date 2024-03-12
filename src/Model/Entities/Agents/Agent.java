package Model.Entities.Agents;

import java.awt.Color;
import java.util.ArrayList;

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
    private ObjectGrid2D agentGrid;

    // grid where all objects (grass) are stored
    private ObjectGrid2D objectGrid;

    // ===== CONSTRUCTORS =====

    public Agent(int id, Color color, int energy, ObjectGrid2D agentGrid, ObjectGrid2D objectGrid)
    {
        super(id, color);
        this.energy = energy;
        this.location = null;
        this.agentGrid = agentGrid;
        this.objectGrid = objectGrid;
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
    public Neighbourhood checkNeighbours()
    {
        Neighbourhood[] neighbours = new Neighbourhood[4];
        Entity top;
        Int2D top_location;
        Entity bottom;
        Int2D bottom_location;
        Entity left;
        Int2D left_location;
        Entity right;
        Int2D right_location;

        // query each direction

        // check agent grid
        // look above
        top_location = new Int2D(location.getX(), location.getY() - 1);
        top = (Entity) this.agentGrid.get(top_location.getX(), top_location.getY());
        neighbours[0] = new Neighbourhood(top, top_location);

        // look below
        bottom_location = new Int2D(location.getX(), location.getY() + 1);
        bottom = (Entity) this.agentGrid.get(bottom_location.getX(), bottom_location.getY() + 1);
        neighbours[1] = new Neighbourhood(bottom, bottom_location);

        // look left
        left_location = new Int2D(location.getX() - 1, location.getY());
        left = (Entity) this.agentGrid.get(left_location.getX() - 1, left_location.getY());
        neighbours[2] = new Neighbourhood(left, left_location);

        // look right
        right_location = new Int2D(location.getX() + 1, location.getY());
        right = (Entity) this.agentGrid.get(right_location.getX() + 1, right_location.getY());
        neighbours[3] = new Neighbourhood(right, right_location);

        // find indices of missing neighbours (no agent present)

        // storage for all neighbours that are "null" (no agent present)
        ArrayList<Integer> nullindices = new ArrayList<Integer>();

        for (int i = 0; i < neighbours.length; i++)
        {
            if (neighbours[i] == null) 
            {
                // add index of "o"
                nullindices.add(i);
            }    
        }

        // for each "null" (no agent found) add a grass object from the object grid
        for (int i = 0; i < nullindices.size(); i++)
        {
            
            // get Neighbourhood with null value as an Entity
            Neighbourhood nullEntityNeighbourhood = neighbours[nullindices.get(i)];

            // get location of this neighbourhoof
            Int2D nullEntityNeighbourhoodLocation = nullEntityNeighbourhood.getneighbourLocation();

            // change Entity to Grass Object from objectGrid (meadow)
            Entity grass_entity = this.objectGrid.get(nullEntityNeighbourhoodLocation.getX(), nullEntityNeighbourhoodLocation.getY());

            nullEntityNeighbourhood.setNeighbour(grass_entity);

        }
        


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
            (location.getX() < 0 || location.getX() > this.agentGrid.getWidth()) || 
            (location.getY() < 0 || location.getY() > this.agentGrid.getHeight())
        )
        {
            throw new IllegalArgumentException("Location is out of bound!");
        }

        this.location = location;
    }

    public ObjectGrid2D getagentGrid() 
    {
       return this.agentGrid;
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
