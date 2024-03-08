package Model;
import Model.Agents.*;
import sim.engine.*;
import sim.field.grid.ObjectGrid2D;

public class Model extends SimState 
{

    // ===== ATTRIBUTES =====


    // create spatial representation for the model (a "field"). This is where all agents "live"
    private ObjectGrid2D meadow = new ObjectGrid2D(10, 10);

    // sets number of sheeps in this simulation
    private int sheeps = 5;

    // sets number of wolves in this simulation
    private int wolves = 5;


    public Model(long seed)
    {
    super(seed);
    }

    // ===== METHODS =====
    
    /**
     * This function is run once on model start up. Here you can:
     * - populate the meadow with animals
     * - dasd
     */ 
    public void start()
    {
        // call implementaion of super method
        super.start();

        // clear spatial representation of the model
        meadow.clear();

        // populate meadow with wolves
        for (int i = 0; i < this.wolves; i++)
        {
            // randomly set agents on the meadow
            Wolve wolve = new Wolve(i);

            // find a random, empty cell in the grid

            // draw random int from 0 till grid.getWidth()
            int x = random.nextInt(meadow.getWidth());
            int y = random.nextInt(meadow.getWidth());


            meadow.set(x,y, wolve);

        }

        // populate meadow with sheeps
        for (int i = 0; i < this.sheeps; i++)
        {
            // randomly set agents on the meadow
            Sheep sheep = new Sheep(i);

            // find a random, empty cell in the grid
            
            // draw random int from 0 till grid.getWidth()
            int x = random.nextInt(meadow.getWidth());
            int y = random.nextInt(meadow.getWidth());

            meadow.set(x,y, sheep);

        }


        for (int i = 0; i < meadow.elements().size(); i++)
        {
            System.out.println(meadow.elements().get(i));
        }

        
    }


    // ===== GETTER & SETTER =====
    public int getSheeps() {
        return this.sheeps;
    }

    public void setSheeps(int value) throws IllegalArgumentException{

        if (value < 0) throw new IllegalArgumentException("Value has to be greater than 0!");
        
        this.sheeps = value;
    }

    public int getWolves() {
        return this.wolves;
    }

    public void setWolves(int value) throws IllegalArgumentException{

        if (value < 0) throw new IllegalArgumentException("Value has to be greater than 0!");

        this.wolves = value;
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
