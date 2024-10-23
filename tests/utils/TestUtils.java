/*
Copyright (C) 2024 Michael Brüggemann

This file is part of Sheepsmeadow.

Sheepsmeadow is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Sheepsmeadow is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Sheepsmeadow. If not, see <https://www.gnu.org/licenses/>.
*/

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
                Grass grass = new Grass(null);

                grass.addToLocation(grid, i, j);
                grass_id++;
            }
        }
    }
}
