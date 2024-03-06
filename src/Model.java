import sim.engine.*;

public class Model extends SimState 
{
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
