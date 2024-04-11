package Model;

import sim.engine.*;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;

import java.util.Stack;

import Model.Entities.*;
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
     * Populates the grid with Agents. Each Grid cell will be filled with a "Stack<Entity>". Each Stack gets a "Grass" object as it's first entry, which will never be removed from the stack. Afterwards the Agents will be placed in the grid (added to a Stack). Filled Cells will have Stacks of size 2, each other will be size 1.
     */
    public void populateMeadow()
    {
     
        int grass_id = 0;

        // initialize each cell of the grid with a Stack and add a Grass object to it
        for (int i = 0; i < meadow.getHeight(); i++)
        {
            for (int j = 0; j < meadow.getWidth(); j++)
            {
                
                Stack<Entity> stack = new Stack<Entity>();
                stack.push(new Grass(grass_id));

                meadow.set(i,j, stack);
                grass_id++;
            }
        }
        
        // counters to keep track of the number of already added agents
        int sheep_counter = 0;
        int wolf_counter = 0;

        int num_individuals = this.wolves + this.sheeps;

        while (sheep_counter + wolf_counter != num_individuals)
        {
            // Add sheeps
            if (sheep_counter < this.sheeps)
            {
                Sheep sheep = new Sheep(sheep_counter, 20, this.meadow);

                // find a random, empty cell in the grid
                while (true) 
                {
                    // draw random int from 0 till gridsize
                    int x = random.nextInt(meadow.getWidth());
                    int y = random.nextInt(meadow.getHeight());

                    // get stack of the cell
                    @SuppressWarnings("unchecked")
                    Stack<Entity> stack = (Stack<Entity>) meadow.get(x,y);

                    // if cell is "empty" (only a grass object is present)
                    if (stack.size() == 1)
                    {
                        stack.push(sheep);

                        // update agent location
                        sheep.setLocation(new Int2D(x, y));

                        break;
                    }
                }

                // add Entity to the schedule
                Stoppable scheduleStopper = this.schedule.scheduleRepeating(sheep);
                sheep.setScheduleStopper(scheduleStopper);

                sheep_counter++;
            }

            // Add wolves
            if (wolf_counter < this.wolves)
            {
                Wolf wolf = new Wolf(wolf_counter, 20, this.meadow);

                // find a random, empty cell in the grid
                while (true) 
                {
                    // draw random int from 0 till gridsize
                    int x = random.nextInt(meadow.getWidth());
                    int y = random.nextInt(meadow.getHeight());

                    // get stack of the cell
                    @SuppressWarnings("unchecked")
                    Stack<Entity> stack = (Stack<Entity>) meadow.get(x,y);

                    // if cell is "empty" (only a grass object is present)
                    if (stack.size() == 1)
                    {
                        stack.push(wolf);

                        // update agent location
                        wolf.setLocation(new Int2D(x, y));

                        break;
                    }
                }

                // add Entity to the schedule
                Stoppable scheduleStopper = this.schedule.scheduleRepeating(wolf);
                wolf.setScheduleStopper(scheduleStopper);
                
                wolf_counter++;
            }
        }
    }


    // ===== GETTER & SETTER =====

    public int getSheeps() 
    {
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

    public int getWolves() 
    {
        return this.wolves;
    }

    public void setWolves(int value) throws IllegalArgumentException, Exception
    {

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
