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

        modelInstance.addGrassToGrid();
    }
}
