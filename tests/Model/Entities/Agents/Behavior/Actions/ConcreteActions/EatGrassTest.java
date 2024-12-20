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

package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Wolf;
import Model.Entities.Objects.Grass;
import Model.Exceptions.GridLocationOccupiedException;
import Model.Neighbourhood.Neighbourhood;

import sim.engine.Stoppable;
import utils.TestModel;


public class EatGrassTest {
    
    TestModel testModel;
    Sheep sheep;
    EatGrass eatGrass;

    @Before
    public void setUp() {

        this.testModel = new TestModel();

        // create a lonely sheep agent
        this.sheep = new Sheep(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);

        // add sheep to the model schedule
        Stoppable scheduleStopper = testModel.modelInstance.schedule.scheduleRepeating(sheep);
        this.sheep.setScheduleStopper(scheduleStopper);

        // create instance of EatGrass Action
        this.eatGrass = new EatGrass(0);
    }

    @After
    public void cleanup()
    {
        // remove all Entities and add new grass
        this.testModel.modelInstance.returnMeadow().clear();
        this.testModel.modelInstance.addGrassToGrid();
    }

    @Test
    public void testExecute() throws GridLocationOccupiedException
    {
        // place sheep into the grid
        this.sheep.updateGridLocationTo(1, 1, false);

        // get Neighbourhood of the sheep
        Neighbourhood neighbourhood = this.sheep.checkNeighbours();

        // get current energy level before eating
        int energyBeforeEating = this.sheep.getEnergy();

        System.out.println("Energy before eating: " + energyBeforeEating);

        // execute the action
        this.eatGrass.execute(sheep, neighbourhood, this.testModel.modelInstance);

        System.out.println("Energy after eating: " + this.sheep.getEnergy());

        // get the grass object of the cell the sheep has moved to
        Grass grass = this.sheep.getGrasscell();

        // TEST: grass should be regrowing
        assertTrue(grass.getIsRegrowing());

        // TEST: the sheeps energy should be increased by 4
        assertEquals(energyBeforeEating + 4, this.sheep.getEnergy());

        int regrowthCounterBeforeStep = grass.getRegrowthCounter();

        // remove the sheep from the schedule to have no interference
        this.sheep.getScheduleStopper().stop();

        // Step the schedule once
        this.testModel.modelInstance.schedule.step(this.testModel.modelInstance);

        // TEST: regrowthcounter of grass should decreamented by 1
        assertEquals(regrowthCounterBeforeStep - 1, grass.getRegrowthCounter());

        // Step the schedule 2 more times
        this.testModel.modelInstance.schedule.step(this.testModel.modelInstance);
        this.testModel.modelInstance.schedule.step(this.testModel.modelInstance);

        // TEST: grass should be regrown
        assertTrue(!grass.getIsRegrowing());
    }

    @Test
    public void testCheckCondition() throws GridLocationOccupiedException
    {
        // place sheep into the grid
        this.sheep.updateGridLocationTo(0, 0, false);

        // occupy the neighbouring cells
        Wolf w1 = new Wolf(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);
        Wolf w2 = new Wolf(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);

        w1.updateGridLocationTo(0, 1, false);
        w2.updateGridLocationTo(1, 0, false);

        // get Neighbourhood
        Neighbourhood neighbourhood = this.sheep.checkNeighbours();

        System.out.println("Neighbourhood: " + neighbourhood.toString());

        // TEST: condition isn't fullfilled (no neighbouring  grass cells)
        assertTrue(!this.eatGrass.checkCondition(this.sheep, neighbourhood, this.testModel.modelInstance));

        // remove neighbours
        w1.getGrasscell().addToLocation(this.testModel.modelInstance.returnMeadow(), 0, 1);
        w2.getGrasscell().addToLocation(this.testModel.modelInstance.returnMeadow(), 1, 0);

        // re-check neighbourhood after removing the neighbours
        neighbourhood = this.sheep.checkNeighbours();

        System.out.println("Neighbourhood: " + neighbourhood.toString());

        // TEST: condition is fullfilled
        assertTrue(this.eatGrass.checkCondition(this.sheep, neighbourhood, this.testModel.modelInstance));
    }
}
