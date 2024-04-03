package Model.Entities.Agents;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Model.Entities.Entity;
import Model.Entities.Objects.Grass;
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
    public void testCheckNeighbours()
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
        Stack<Entity> stack = (Stack<Entity>) grid.get(1,1);
        stack.push(agent);
        agent.setLocation(new Int2D(1, 1));
        Stack<Entity> top = (Stack<Entity>) grid.get(0,1);
        top.push(w1);
        w1.setLocation(new Int2D(0, 1));
        Stack<Entity> left = (Stack<Entity>) grid.get(1,0);
        left.push(w2);
        w2.setLocation(new Int2D(1, 0));
        Stack<Entity> right = (Stack<Entity>) grid.get(2,1);
        right.push(s1);
        s1.setLocation(new Int2D(2, 1));
        Stack<Entity> bottom = (Stack<Entity>) grid.get(1,2);
        bottom.push(s2);
        s2.setLocation(new Int2D(1, 2));

        Neighbourhood neighbour = agent.checkNeighbours();

        assertEquals(Sheep.class, neighbour.getNeighbour().getClass());

    }
}
