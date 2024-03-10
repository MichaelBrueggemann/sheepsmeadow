package Model.Agents;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import Model.Entities.Agents.Agent;
import Model.Entities.Agents.Wolve;

import java.awt.Color;


public class AgentTest {

    private Agent agent;

    @Before
    public void setUp() {
        // Initialize the agent instance before each test
        agent = new Wolve(1, Color.RED); 
    }

    @Test
    public void testAgentId() {
        assertEquals(1, agent.getId());
    }

    @Test
    public void testAgentColor() {
        assertEquals(Color.RED, agent.getColor());
    }

    // Additional test cases can be added here
}
