package Model;

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
        grid = this.model.returnMeadow();
    }


    @Test
    public void testPopulateMeadow()
    {
        model.populateMeadow();

        assertTrue(
            "Number of Agents exceeds Individuals Limit!", 
            this.grid.elements().size() <= model.returnMAX_INDIVIDUALS()
        );
    }
}
