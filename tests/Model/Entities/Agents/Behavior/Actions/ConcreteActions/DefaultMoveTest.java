package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import static org.junit.Assert.*;

import org.junit.*;

import Model.Entities.Agents.Sheep;
import Model.Entities.Agents.Wolf;
import Model.Neighbourhood.Neighbourhood;
import sim.engine.Stoppable;
import utils.TestModel;

public class DefaultMoveTest {
    TestModel testModel;
    Sheep sheep;
    DefaultMove defaultMove;

    @Before
    public void setUp() {

        this.testModel = new TestModel();

        // create a lonely sheep agent
        this.sheep = new Sheep(1, 20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random);

        // add sheep to the model schedule
        Stoppable scheduleStopper = testModel.modelInstance.schedule.scheduleRepeating(sheep);
        this.sheep.setScheduleStopper(scheduleStopper);

        // create instance of EatGrass Action
        this.defaultMove = new DefaultMove(0);
    }

    @After
    public void cleanup()
    {
        // remove all Entities and add new grass
        this.testModel.modelInstance.returnMeadow().clear();
        this.testModel.modelInstance.addGrassToGrid();
    }

    @Test
    public void testCheckCondition()
    {
        // place sheep into the grid
        this.sheep.updateGridLocationTo(0, 0, false);

        // occupy the neighbouring cells
        Wolf w1 = new Wolf(1, 20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random);
        Wolf w2 = new Wolf(2, 20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random);

        w1.updateGridLocationTo(0, 1, false);
        w2.updateGridLocationTo(1, 0, false);

        // get Neighbourhood
        Neighbourhood neighbourhood = this.sheep.checkNeighbours();

        System.out.println("Neighbourhood without free cells: " + neighbourhood.toString());

        // TEST: condition isn't fullfilled (no free neighbouring cells)
        assertTrue(!this.defaultMove.checkCondition(neighbourhood));

        // remove neighbours
        w1.getGrasscell().addToLocation(this.testModel.modelInstance.returnMeadow(), 0, 1);
        w2.getGrasscell().addToLocation(this.testModel.modelInstance.returnMeadow(), 1, 0);

        // re-check neighbourhood after removing the neighbours
        neighbourhood = this.sheep.checkNeighbours();

        System.out.println("Neighbourhood with free cells: " + neighbourhood.toString());

        // TEST: condition is fullfilled
        assertTrue(this.defaultMove.checkCondition(neighbourhood));
    }
}
