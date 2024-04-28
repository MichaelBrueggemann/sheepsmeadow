package View;

import sim.display.Display2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.grid.ObjectGridPortrayal2D;
import sim.portrayal.simple.RectanglePortrayal2D;

import java.awt.*;

import Model.Model;
import Model.Entities.*;
import Model.Entities.Agents.Agent;
import Model.Entities.Objects.Grass;

/**
 * This class is a collection of funtionality, do provide a Display GUI for the Grid of the model (meadow). This displays the grid itself and also every Entitity that is placed in the Grid.
 */
public class MeadowDisplay 
{
    
    public static void setupPortrayal(ObjectGridPortrayal2D meadowPortrayal, Model state, Display2D display)
    {
        // disable grid borders
        meadowPortrayal.setBorder(false);

        // Set custom portrayal for each cell
        meadowPortrayal.setPortrayalForAll(
                // draw a rectangle graphic for each grid cell
                new RectanglePortrayal2D() 
                {
                    @Override
                    // Overrides draw method for custom entity colors
                    public void draw(Object object, Graphics2D graphics, DrawInfo2D info) 
                    {
                        if (object != null)
                        {
                            Entity entity = (Entity) object;

                            paint = entity.getColor(); 

                            // draw the rectangle
                            super.draw(object, graphics, info);

                            // draw a label displaying the agents energy
                            if (entity instanceof Agent)
                            {
                                Agent agent = (Agent) entity;

                                // draw the label
                                String label = Integer.toString(agent.getEnergy()); // Or whatever logic you use to get the label

                                // get width of the string and height of the font to correct the position of the label
                                FontMetrics fm = graphics.getFontMetrics();
                                int textWidth = fm.stringWidth(label);
                                int textHeight = fm.getHeight();

                                // info.draw.<coord> indicates the center of the rectangle that should be drawn
                                int centerX = (int) info.draw.x - textWidth / 2;
                                int centerY = (int) info.draw.y + textHeight / 2;

                                graphics.setColor(Color.WHITE);
                                graphics.drawString(label, centerX, centerY);
                            }

                            // only draw labels for grass cells if they are "regrowing"
                            if (entity instanceof Grass && ((Grass) entity).getIsRegrowing())
                            {
                                Grass grass = (Grass) entity;

                                // draw the label
                                String label = Integer.toString(grass.getRegrowthCounter()); // Or whatever logic you use to get the label

                                // get width of the string and height of the font to correct the position of the label
                                FontMetrics fm = graphics.getFontMetrics();
                                int textWidth = fm.stringWidth(label);
                                int textHeight = fm.getHeight();

                                // info.draw.<coord> indicates the center of the rectangle that should be drawn
                                int centerX = (int) info.draw.x - textWidth / 2;
                                int centerY = (int) info.draw.y + textHeight / 2;

                                graphics.setColor(Color.WHITE);
                                graphics.drawString(label, centerX, centerY);
                            }
                            
                        }
                    }
                }
        );


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
