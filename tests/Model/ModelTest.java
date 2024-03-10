package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import sim.field.grid.ObjectGrid2D;

public class ModelTest {
    
    Model model;
    ObjectGrid2D grid;

    @Before
    public void setUp() {
        // Initialize the model instance before each test
        model = new Model(System.currentTimeMillis());
        grid = this.model.getMeadow();
    }


    @Test
    public void testGridsize()
    {
        int testsize = 20;
        assertEquals(testsize, grid.getHeight());
        assertEquals(testsize, grid.getWidth());
    }

    @Test
    public void testPopulateMeadow()
    {
        model.populateMeadow();

        assertTrue(
            "Number of Agents exceeds Individuals Limit!", 
            this.grid.elements().size() <= model.getMAX_INDIVIDUALS()
        );
    }
}
