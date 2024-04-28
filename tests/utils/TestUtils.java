package utils;

import Model.Entities.Objects.Grass;
import sim.field.grid.ObjectGrid2D;

/**
 * This class provides some Model related functions as utilities to make testing the model easier.
 */
public class TestUtils {
    /**
     * Utitlity function to make writing these tests easier
     */
    public static void addGrassToGrid(ObjectGrid2D grid)
    {
        // fill grid with Grass Entities
        int grass_id = 0;

        // initialize each cell of the grid with a Grass object
        for (int i = 0; i < grid.getHeight(); i++)
        {
            for (int j = 0; j < grid.getWidth(); j++)
            {
                Grass grass = new Grass(grass_id, null);

                grass.addToLocation(grid, i, j);
                grass_id++;
            }
        }
    }
}
