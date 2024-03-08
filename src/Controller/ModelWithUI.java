package Controller;
import sim.engine.*;
import Model.Model;
import sim.display.*;


/**
 * This class is a wrapper for the common simulation class "Model". It's entirely depend on "Model" 
 * (all simulation logic is defined there). This class is used to start, stop, serialize and modify a simulation
 * via a simple GUI.
 */
public class ModelWithUI extends GUIState {
    
    public static void main(String[] args)
    {
        // create simulation state
        ModelWithUI simulation = new ModelWithUI();
        
        // create GUI console
        Console console = new Console(simulation);
        console.setVisible(true);
    }

    public ModelWithUI() 
    { 
        // create a new simulation with current time as a seed
        super(new Model(System.currentTimeMillis())); 
    }

    /**
     * Takes state passed by the default constructor to construct a GUIState.
     * @param state Instance of "SimState"
     */
    public ModelWithUI(SimState state) 
    { 
        super(state); 
    }
    
    public static String getName() 
    { 
        return "Sheepsmeadow"; 
    }
}
