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



    public static void main(String[] args)
    {
        // run the simulation with given model class file
        doLoop(Model.class, args);

        // exit main process to ensure all threads have stopped
        System.exit(0); 
    }
}
