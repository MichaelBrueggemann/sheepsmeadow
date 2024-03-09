package Model;

import static org.junit.Assert.assertEquals;

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
        int testsize = 10;
        assertEquals(testsize, grid.getHeight());
        assertEquals(testsize, grid.getWidth());
    }

    @Test
    public void testPopulateMeadow()
    {
        model.populateMeadow();

        int num_individuals = model.getSheeps() + model.getWolves();

        // Test that the grid contains exactly as much objects as are defined in the model
        assertEquals(num_individuals, this.grid.elements().size());
    }
}
