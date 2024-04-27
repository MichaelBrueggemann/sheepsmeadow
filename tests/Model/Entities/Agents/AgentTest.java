package Model.Entities.Agents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Model.Entities.Entity;
import Model.Entities.Objects.Grass;
import Model.Exceptions.GridPositionOccupiedException;
import Model.Neighbourhood.Cell;
import Model.Neighbourhood.Neighbourhood;
import ec.util.MersenneTwisterFast;
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

        MersenneTwisterFast rng = new MersenneTwisterFast();
        
        // create artificial , empty grid
        this.grid = new ObjectGrid2D(10, 10);

        // Initialize the agent instance before each test
        agent = new Wolf(1, 20, grid, rng); 
    }


    @Test
    public void testAgentColor() {
        assertEquals(Color.gray, agent.getColor());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCheckNeighbours() throws GridPositionOccupiedException
    {
        // fill grid with Grass Entities
        int grass_id = 0;

        // random RNG, dont used in this test
        MersenneTwisterFast rng = new MersenneTwisterFast();

        for (int i = 0; i < grid.getHeight(); i++)
        {
            for (int j = 0; j < grid.getWidth(); j++)
            {
                
                Stack<Entity> stack = new Stack<Entity>();
                stack.push(new Grass(grass_id, rng));

                grid.set(i,j, stack);
                grass_id++;
            }
        }

        Wolf w2 = new Wolf(2, 20, this.grid, rng);
        Sheep s1 = new Sheep(1,20, this.grid, rng);
        Wolf w3 = new Wolf(3, 20, this.grid, rng);
        Sheep s2 = new Sheep(2,20, this.grid, rng);
        
        Int2D middle = new Int2D(1, 1);
        Int2D left = new Int2D(0, 1);
        Int2D top = new Int2D(1, 0);
        Int2D right = new Int2D(2, 1);
        Int2D bottom = new Int2D(1, 2);

        Int2D[] neighbour_positions = {left, top, right, bottom};

        // fill grid with some agent combinations
        // CASE 1: all neighbouring cells contain Entities

        // just set the location once, so that all agents have a initial location
        Stack<Entity> stack = (Stack<Entity>) grid.get(middle.getX(), middle.getY());
        agent.setLocation(middle);
        stack.push(agent);


        w3.setLocation(top);
        stack = (Stack<Entity>) grid.get(top.getX(), top.getY());
        stack.push(w3);

        s1.setLocation(right);
        stack = (Stack<Entity>) grid.get(right.getX(), right.getY());
        stack.push(s1);

        s2.setLocation(bottom);
        stack = (Stack<Entity>) grid.get(bottom.getX(), bottom.getY());
        stack.push(s2);

        w2.setLocation(left);
        stack = (Stack<Entity>) grid.get(left.getX(), left.getY());
        stack.push(w2);

        Neighbourhood neighbours = agent.checkNeighbours();
        Neighbourhood correctNeighbourhood = new Neighbourhood(new Cell(w3, top), new Cell(s1, right), new Cell(s2, bottom), new Cell(w2, left));

        assertEquals(correctNeighbourhood.getTop().getEntity(), neighbours.getTop().getEntity());
        assertEquals(correctNeighbourhood.getRight().getEntity(), neighbours.getRight().getEntity());
        assertEquals(correctNeighbourhood.getBottom().getEntity(), neighbours.getBottom().getEntity());
        assertEquals(correctNeighbourhood.getLeft().getEntity(), neighbours.getLeft().getEntity());

        // CASE 2: no neighbours

        // empty the neighbouring positions
        for (Int2D position : neighbour_positions)
        {
            Model.emptyGridCell(this.grid, position.getX(), position.getY());
        }

        // check Neighbourhood
        neighbours = agent.checkNeighbours();

        for (Cell n : neighbours.getAllNeighbours())
        {
            assertEquals(Grass.class, n.getEntity().getClass());
        }
        

        // CASE 3: some neighbouring cells are out of bounds

        middle = new Int2D(0,0);
        // top = null;
        Model.emptyGridCell(this.grid, 1, 0);
        right = new Int2D(1,0);

        bottom = new Int2D(0,1);
        // left = null;

        agent.updateGridPosition(middle.getX(), middle.getY());

        w2.updateGridPosition(right.getX(), right.getY());

        s1.updateGridPosition(bottom.getX(), bottom.getY());
        correctNeighbourhood = new Neighbourhood(null, new Cell(w2, right), new Cell(s1, bottom), null);

        // check Neighbourhood
        neighbours = agent.checkNeighbours();

        for (Cell n : neighbours.getAllNeighbours())
        {
            if (n != null) System.out.println(n.getEntity());
        }

        assertEquals(correctNeighbourhood.getTop(), neighbours.getTop());
        assertEquals(correctNeighbourhood.getRight().getEntity(), neighbours.getRight().getEntity());
        assertEquals(correctNeighbourhood.getBottom().getEntity(), neighbours.getBottom().getEntity());
        assertEquals(correctNeighbourhood.getLeft(), neighbours.getLeft());

        // check neighbourhood position
        assertTrue("Neighbour from the wrong grid position was choosen!", neighbours.getBottom().getLocation().getX() == bottom.getX() && neighbours.getBottom().getLocation().getY() == bottom.getY());

    }
}
