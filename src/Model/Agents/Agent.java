package Model.Agents;
import java.awt.Color;

/**
 * This is a abstract class defining general behavior and attributes of all Agents.
 */
public abstract class Agent {

    private int id;
    private Color color;


    public Agent(int id, Color color)
    {
        this.id = id;
        this.color = color;
    }

    public int getId()
    {
        return this.id;
    }

    public Color getColor()
    {
        return this.color;
    }
}
