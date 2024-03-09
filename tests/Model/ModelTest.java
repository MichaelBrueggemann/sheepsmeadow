package Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import sim.field.grid.ObjectGrid2D;

public class ModelTest {
    
    Model model;

    @Before
    public void setUp() {
        // Initialize the model instance before each test
        model = new Model(System.currentTimeMillis());
    }


    @Test
    public void testGridsize()
    {
        int testsize = 10;
        ObjectGrid2D grid = this.model.getMeadow();
        assertEquals(testsize, grid.getHeight());
        assertEquals(testsize, grid.getWidth());
    }
}
