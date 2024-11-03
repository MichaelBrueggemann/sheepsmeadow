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


import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Entities.Agents.Wolf;
import Model.Entities.Agents.Sheep;

import Model.Exceptions.GridLocationOccupiedException;
import Model.Neighbourhood.Neighbourhood;
import sim.engine.Stoppable;
import utils.TestModel;


public class EatSheepTest {
    
    TestModel testModel;
    Sheep sheep;
    Wolf wolf;
    EatSheep eatSheep;

    @Before
    public void setUp() {

        this.testModel = new TestModel();

        // create a lonely sheep agent
        this.sheep = new Sheep(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);

        // add sheep to the model schedule
        Stoppable scheduleStopperS = testModel.modelInstance.schedule.scheduleRepeating(sheep);
        this.sheep.setScheduleStopper(scheduleStopperS);

         // create a wolf agent
         this.wolf = new Wolf(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);

         // add sheep to the model schedule
         Stoppable scheduleStopperW = testModel.modelInstance.schedule.scheduleRepeating(sheep);
         this.sheep.setScheduleStopper(scheduleStopperW);

        // create instance of EatSheep Action
        this.eatSheep = new EatSheep(0);
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
        
    }

    @Test
    public void testCheckCondition() throws GridLocationOccupiedException
    {
        // place sheep into the grid
        this.sheep.updateGridLocationTo(1, 1, false);
        // place sheep into the grid
        this.wolf.updateGridLocationTo(0, 1, false);

        // get Neighbourhood of the sheep
        Neighbourhood neighbourhood = this.wolf.checkNeighbours();

        System.out.println(neighbourhood.getAllNeighbours());

        // TEST: wolf should be able to eat the sheep
        assertTrue(eatSheep.checkCondition(wolf, neighbourhood, this.testModel.modelInstance));

        // un-alive sheep
        sheep.die();

        // TEST: sheep agent should be dead now
        assertTrue(!sheep.isAlive());

        // TEST: wolf shouldn't be able to eat the sheep now
        assertTrue(!eatSheep.checkCondition(wolf, neighbourhood, this.testModel.modelInstance));

    }
}
