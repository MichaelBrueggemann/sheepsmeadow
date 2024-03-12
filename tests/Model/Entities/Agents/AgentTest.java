package Model.Entities.Agents;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sim.field.grid.ObjectGrid2D;

import java.awt.Color;


public class AgentTest {

    private Agent agent;

    @Before
    public void setUp() {

        // create artificial , empty grid
        ObjectGrid2D grid = new ObjectGrid2D(10, 10);

        // Initialize the agent instance before each test
        agent = new Wolve(1, 20, grid); 
    }

    @Test
    public void testAgentId() {
        assertEquals(1, agent.getId());
    }

    @Test
    public void testAgentColor() {
        assertEquals(Color.gray, agent.getColor());
    }

    // Additional test cases can be added here
}
