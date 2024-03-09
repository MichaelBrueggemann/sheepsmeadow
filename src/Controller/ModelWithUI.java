package Controller;
import sim.engine.*;
import sim.portrayal.*;
import sim.portrayal.grid.ObjectGridPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.*;
import Model.Model;
import Model.Agents.Agent;
import sim.display.*;


/**
 * This class is a wrapper for the common simulation class "Model". It's entirely depend on "Model" 
 * (all simulation logic is defined there). This class is used to start, stop, serialize and modify a simulation
 * via a simple GUI.
 */
public class ModelWithUI extends GUIState {
    
    public Display2D display;
    public JFrame displayFrame;
    public ObjectGridPortrayal2D meadowPortrayal = new ObjectGridPortrayal2D();

    public void setupPortrayals()
    {
        // used to acces the Model instance
        Model model = (Model) state;

        // show grid borders
        meadowPortrayal.setBorder(true);
        meadowPortrayal.setBorderColor(Color.black);

        // Set custom portrayal for each cell
        meadowPortrayal.setPortrayalForAll(
            new RectanglePortrayal2D() 
            {
                @Override
                // Overrides draw method for custom agent colors
                public void draw(Object object, Graphics2D graphics, DrawInfo2D info) 
                {
                    // paint cell, if agent is present
                    if (object != null) 
                    {
                        Agent agent = (Agent) object;

                        // Get color from the Agent class
                        paint = agent.getColor(); 
                    } 
                    else 
                    {
                        // Default color for empty cells
                        paint = Color.green; 
                    }

                    scale = 0.9; // Scale factor to reduce the size of the rectangle

                    super.draw(object, graphics, info);
                }
            });


        // tell the portrayals what to portray and how to portray them
        meadowPortrayal.setField(model.getMeadow());

        // reschedule the displayer
        display.reset();

        display.setBackdrop(Color.white);
        display.setClipping(true);

        // redraw the display after each step of the model schedule
        display.repaint();
    }

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
        setupPortrayals();
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
        setupPortrayals();
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
