package Model.Agents;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgentTest {

    private Agent agent;
    private int id;
    private Color color;

    @BeforeEach
    public void setUp() {
        id = 1;
        color = Color.RED;
        agent = new Wolve(id, color);
    }

    @Test
    public void testAgentId() {
        assertEquals(id, agent.getId());

        // test after polymorph
        agent = new Sheep(id, color);

        assertEquals(id, agent.getId());
    }

    @Test
    public void testAgentColor() {
        assertEquals(color, agent.getColor());

        // test after polymorph
        agent = new Sheep(id, color);

        assertEquals(color, agent.getColor());
    }

    // Additional test cases can be added here
}
