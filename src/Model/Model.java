package Model;
import Model.Agents.*;
import sim.engine.*;
import sim.field.grid.ObjectGrid2D;

public class Model extends SimState 
{
    // create spatial representation for the model (a "field"). This is where all agents "live"
    private ObjectGrid2D meadow = new ObjectGrid2D(10, 10);

    // set maximum number of individuals contained in this model
    private int number_of_individuals = 5;


    public Model(long seed)
    {
    super(seed);
    }

    

    /* 
    This function is run once on model start up. Here you can:
    - populate the meadow with animals
    - 
    */ 
    public void start()
    {
        // call implementaion of super method
        super.start();

        // clear spatial representation of the model
        meadow.clear();

        // populate meadow
        for (int i = 0; i < this.number_of_individuals; i++)
        {
            // randomly set agents on the meadow
            Wolf wolf = new Wolf(i);

            // find a random, empty cell in the grid


            meadow.set(i,i, wolf);

        }

        System.out.println(meadow.elements().get(0));
        System.out.println(meadow.elements().get(1));
        System.out.println(meadow.elements().get(2));
        System.out.println(meadow.elements().get(3));
        
    }

    public static void main(String[] args)
    {
        // run the simulation with given model class file and any arguments given from the terminal
        doLoop(Model.class, args);

        // exit main process to ensure all threads have stopped
        System.exit(0); 
    }
}
