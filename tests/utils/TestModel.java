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
