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

import Model.Model;

/**
 * Example Model with only grass added to the grid.
 * 
 * Just for tests.
 */
public class TestModel {
    
    public Model modelInstance;

    public TestModel()
    {
        this.modelInstance = new Model(1);

        // set fixed gridsize for the test. All Tests assume this gridsize
        this.modelInstance.returnMeadow().reshape(7, 7);

        modelInstance.addGrassToGrid();

        // Wolfs and Sheeps can definitely reproduce 
        modelInstance.setSheepFertilityRate(1.0);
        modelInstance.setWolfFertilityRate(1.0);
    }
}
