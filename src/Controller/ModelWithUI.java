package Controller;

import sim.engine.*;
import sim.portrayal.*;
import sim.portrayal.grid.ObjectGridPortrayal2D;
import sim.display.*;

import javax.swing.*;

import Model.Model;
import View.MeadowDisplay;



/**
 * This class is a wrapper for the common simulation class "Model". It's entirely depend on "Model" 
 * (all simulation logic is defined there). This class is used to start, stop, serialize and modify a simulation
 * via a simple GUI.
 */
public class ModelWithUI extends GUIState {
    
    public Display2D display;
    public JFrame displayFrame;
    public ObjectGridPortrayal2D meadowPortrayal = new ObjectGridPortrayal2D();

    /**
     * Called on GUI creation
     */
    public void init(Controller c)
    {
        super.init(c);

        // construct Display in the size of the grid used in "model"
        display = new Display2D(300, 300, this);

        displayFrame = display.createFrame();
        displayFrame.setTitle("Sheepsmeadow Display");

        c.registerFrame(displayFrame); // so the frame appears in the "Display" list
        displayFrame.setVisible(true);
        display.attach( meadowPortrayal, "Meadow" );
    }

    /**
     * Called, when the "play" Button in the GUI is pressed.
     */
    public void start()
    {
        super.start();
        MeadowDisplay.setupPortrayal(meadowPortrayal, (Model) state, display);
    }

    /**
     * Called on destruction of the GUI
     */
    public void quit()
    {
        super.quit();

        if (displayFrame!=null) displayFrame.dispose();

        displayFrame = null;
        display = null;
    }
    
    public void load(SimState state)
    {
        super.load(state);
        MeadowDisplay.setupPortrayal(meadowPortrayal, (Model) state, display);
    }

    public static void main(String[] args)
    {
        // create simulation state
        ModelWithUI simulation = new ModelWithUI();
        
        // create GUI console
        Console console = new Console(simulation);
        console.setVisible(true);
    }


    public Object getSimulationInspectedObject() 
    { 
        return state;
    }

    @Override 
    /**
     * Adds an Inspector Panel "Model" to the GUI. Used to edit Model Attributes
     */
    public Inspector getInspector()
    {
        Inspector i = super.getInspector();
        i.setVolatile(false);

        return i;
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
