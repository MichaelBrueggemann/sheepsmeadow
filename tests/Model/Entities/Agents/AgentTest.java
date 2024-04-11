package Model.Entities.Agents;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Model.Entities.Entity;
import Model.Entities.Objects.Grass;
import Model.Exceptions.GridPositionOccupiedException;
import Model.Neighbourhood.Neighbourhood;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;

import java.awt.Color;
import java.util.Stack;


public class AgentTest {

    private Agent agent;
    private ObjectGrid2D grid;

    @Before
    public void setUp() {

        // create artificial , empty grid
        this.grid = new ObjectGrid2D(10, 10);

        // Initialize the agent instance before each test
        agent = new Wolf(1, 20, grid); 
    }


    @Test
    public void testAgentColor() {
        assertEquals(Color.gray, agent.getColor());
    }

    @Test
    public void testCheckNeighbours() throws GridPositionOccupiedException
    {
        // fill grid with Grass Entities
        int grass_id = 0;

        for (int i = 0; i < grid.getHeight(); i++)
        {
            for (int j = 0; j < grid.getWidth(); j++)
            {
                
                Stack<Entity> stack = new Stack<Entity>();
                stack.push(new Grass(grass_id));

                grid.set(i,j, stack);
                grass_id++;
            }
        }

        Wolf w1 = new Wolf(2, 20, this.grid);
        Sheep s1 = new Sheep(1,20, this.grid);
        Wolf w2 = new Wolf(3, 20, this.grid);
        Sheep s2 = new Sheep(2,20, this.grid);
        

        // fill grid with some agent combinations
        // CASE 1: all neighbouring cells contain Entities
        agent.updateGridPosition(1, 1);
        w1.updateGridPosition(0, 1);
        w2.updateGridPosition(1, 0);
        s1.updateGridPosition(2, 1);
        s2.updateGridPosition(1, 2);

        Neighbourhood neighbour = agent.checkNeighbours();

        // the return should be of class sheep, as "agent" is of class Wolf and Sheeps are highest on its priority list
        assertEquals(Sheep.class, neighbour.getNeighbour().getClass());

    }
}
