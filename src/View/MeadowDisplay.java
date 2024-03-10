package View;

import sim.display.Display2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.grid.ObjectGridPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;

import java.awt.Color;
import java.awt.Graphics2D;

import Model.Model;
import Model.Entities.*;

/**
 * This class is a collection of funtionality, do provide a Display GUI for the Grid of the model (meadow).
 */
public class MeadowDisplay 
{
    
    public static void setupPortrayal(ObjectGridPortrayal2D meadowPortrayal, Model state, Display2D display)
    {
        // show grid borders
        meadowPortrayal.setBorder(false);
        meadowPortrayal.setBorderColor(Color.black);

        // Set custom portrayal for each cell
        meadowPortrayal.setPortrayalForAll(
            new RectanglePortrayal2D() 
            {
                @Override
                // Overrides draw method for custom entity colors
                public void draw(Object object, Graphics2D graphics, DrawInfo2D info) 
                {
                    // paint cell, if entity is present
                    if (object instanceof Entity) 
                    {
                        Entity entity = (Entity) object;

                        // Get color from the Entity class
                        paint = entity.getColor(); 
                    } 

                    scale = 1.0; // Scale factor to reduce the size of the rectangle

                    super.draw(object, graphics, info);
                }
            });


        // tell the portrayals what to portray and how to portray them
        meadowPortrayal.setField(state.getMeadow());

        // reschedule the displayer
        display.reset();

        display.setBackdrop(Color.white);
        display.setClipping(true);

        // redraw the display after each step of the model schedule
        display.repaint();
    }

}
