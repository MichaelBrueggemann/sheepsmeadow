package Model.Entities.Agents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Model.Entities.Entity;
import Model.Entities.Objects.Grass;
import Model.Exceptions.GridPositionOccupiedException;
import Model.Neighbourhood.Neighbourhood;
import Model.Model;
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
        
        Int2D middle = new Int2D(1, 1);
        Int2D left = new Int2D(0, 1);
        Int2D top = new Int2D(1, 0);
        Int2D right = new Int2D(1, 2);
        Int2D bottom = new Int2D(2, 1);

        Int2D[] neighbour_positions = {left, top, right, bottom};

        // fill grid with some agent combinations
        // CASE 1: all neighbouring cells contain Entities
        agent.updateGridPosition(middle.getX(), middle.getY());
        w1.updateGridPosition(left.getX(), left.getY());
        w2.updateGridPosition(top.getX(), top.getY());
        s1.updateGridPosition(right.getX(), right.getY());
        s2.updateGridPosition(bottom.getX(), bottom.getY());

        Neighbourhood neighbour = agent.checkNeighbours();

        // the return should be of class sheep, as "agent" is of class Wolf and Sheeps are highest on its priority list
        assertEquals(Sheep.class, neighbour.getNeighbour().getClass());

        // CASE 2: no neighbours

        // empty the neighbouring positions
        for (Int2D position : neighbour_positions)
        {
            Model.emptyGridCell(this.grid, position.getX(), position.getY());
        }

        // check Neighbourhood
        neighbour = agent.checkNeighbours();

        assertEquals(Grass.class, neighbour.getNeighbour().getClass());


        // CASE 3: some neighboruing cells are out of bounds

        middle = new Int2D(0,0);
        right = new Int2D(0,1);
        bottom = new Int2D(1,0);

        w1.updateGridPosition(right.getX(), right.getY());
        s1.updateGridPosition(bottom.getX(), bottom.getY());

        // check Neighbourhood
        neighbour = agent.checkNeighbours();
        assertEquals(Sheep.class, neighbour.getNeighbour().getClass());

        // check neighbourhood position
        assertTrue("Neighbour from the wrong grid position was choosen!", neighbour.getneighbourLocation().getX() == bottom.getX() && neighbour.getneighbourLocation().getY() == bottom.getY());

    }
}
