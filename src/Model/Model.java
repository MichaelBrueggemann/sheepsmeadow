package Model;

import sim.engine.*;
import sim.field.grid.ObjectGrid2D;

import java.awt.Color;

import Model.Entities.Agents.*;
import Model.Entities.Objects.Grass;

public class Model extends SimState 
{
    // ===== ATTRIBUTES =====

    // Size of the grid
    private int gridsize;

    // create spatial representation for the model (a "field"). This is where all agents "live"
    private ObjectGrid2D meadow = new ObjectGrid2D(20, 20);

    // sets number of sheeps in this simulation
    private int sheeps;

    // sets number of wolves in this simulation
    private int wolves;

    // Boundary for maximum number of Agents
    private int MAX_INDIVIDUALS;



    public Model(long seed)
    {   
        super(seed);
        this.MAX_INDIVIDUALS = this.meadow.getHeight() * this.meadow.getWidth();

    }

    // ===== METHODS =====
    

    // ===== MASON CONTROL METHODS =====

    /**
     * This function is run once on model start up. Here you can:
     * - populate the grid with Agents
     * - 
     */ 
    public void start()
    {
        // call implementaion of super method
        super.start();

        // clear spatial representation of the model
        meadow.clear();

        // populate meadow with agents
        this.populateMeadow();
    }

    // ===== HELPER METHODS =====

    /**
     * Populates the grid with Agents. This checks if a cell is empty, before adding the agent to this cell.
     */
    public void populateMeadow()
    {
        int sheep_counter = 0;
        int wolve_counter = 0;

        int num_individuals = this.wolves + this.sheeps;

        while (sheep_counter + wolve_counter != num_individuals)
        {
            // Add sheeps
            if (sheep_counter < this.sheeps)
            {
                Sheep sheep = new Sheep(sheep_counter, Color.white);

                // find a random, empty cell in the grid
                while (true) 
                {
                    // draw random int from 0 till grid.getWidth()
                    int x = random.nextInt(meadow.getWidth());
                    int y = random.nextInt(meadow.getWidth());

                    if (!(meadow.get(x,y) instanceof Sheep || meadow.get(x,y) instanceof Wolve))
                    {
                        meadow.set(x,y, sheep);
                        break;
                    }
                }

                sheep_counter++;
            }

            // Add wolves
            if (wolve_counter < this.wolves)
            {
                Wolve wolve = new Wolve(wolve_counter, Color.gray);

                // find a random, empty cell in the grid
                while (true) 
                {
                    // draw random int from 0 till grid.getWidth()
                    int x = random.nextInt(meadow.getWidth());
                    int y = random.nextInt(meadow.getWidth());

                    if (!(meadow.get(x,y) instanceof Sheep || meadow.get(x,y) instanceof Wolve))
                    {
                        meadow.set(x,y, wolve);
                        break;
                    }
                }

                wolve_counter++;
            }
        }

        int grass_id = 0;

        // Fill rest of the Grid with Grass cells
        for (int i = 0; i < meadow.getHeight(); i++)
        {
            for (int j = 0; j < meadow.getWidth(); j++)
            {
                if (!(meadow.get(i,j) instanceof Sheep || meadow.get(i,j) instanceof Wolve))
                {
                    Grass grass = new Grass(grass_id, Color.green);
                    grass_id++;

                    meadow.set(i,j, grass);
                }
            }
        }
    }


    // ===== GETTER & SETTER =====
    public int getSheeps() {
        return this.sheeps;
    }

    public void setSheeps(int value) throws IllegalArgumentException, Exception{

        if (value < 0) throw new IllegalArgumentException("Value has to be greater than 0!");

        if (this.wolves + value > this.MAX_INDIVIDUALS) throw new Exception("Too much agents");
        
        this.sheeps = value;
    }

    public Object domSheeps()
    {
        return new sim.util.Interval(0, MAX_INDIVIDUALS);
    }

    public int getWolves() {
        return this.wolves;
    }

    public void setWolves(int value) throws IllegalArgumentException, Exception{

        if (value < 0) throw new IllegalArgumentException("Value has to be greater than 0!");

        if (this.sheeps + value > this.MAX_INDIVIDUALS) throw new Exception("Too much agents");

        this.wolves = value;
    }

    public Object domWolves()
    {
        return new sim.util.Interval(0, MAX_INDIVIDUALS);
    }

    public ObjectGrid2D getMeadow() 
    {
        return this.meadow;
    }

    public int getGridsize()
    {
        return this.gridsize;
    }

    public void setGridsize(int value)
    {
        this.gridsize = value;
    }

    public int getMAX_INDIVIDUALS()
    {
        return this.MAX_INDIVIDUALS;
    }

    // ===== MAIN =====

    public static void main(String[] args)
    {
        // run the simulation with given model class file and any arguments given from the terminal
        doLoop(Model.class, args);

        // exit main process to ensure all threads have stopped
        System.exit(0); 
    }

    

    
}
