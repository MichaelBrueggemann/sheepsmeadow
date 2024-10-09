package Model.Entities.Agents.Behavior.Actions.ConcreteActions;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Model.Entities.Agents.Wolf;
import Model.Exceptions.GridLocationOccupiedException;
import Model.Neighbourhood.Cell;
import Model.Neighbourhood.Neighbourhood;

import sim.engine.Stoppable;
import utils.TestModel;


public class ReproduceWolfTest {
    
    TestModel testModel;
    Wolf currentAgent;
    Wolf targetAgent;
    ReproduceWolf reproduceWolf;

    @Before
    public void setUp() {

        this.testModel = new TestModel();

        // create a lonely currentAgent agent
        this.currentAgent = new Wolf(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);

        this.targetAgent = new Wolf(20, this.testModel.modelInstance.returnMeadow(), this.testModel.modelInstance.random, 0);

        // add currentAgent to the model schedule
        Stoppable scheduleStopper = testModel.modelInstance.schedule.scheduleRepeating(currentAgent);
        this.currentAgent.setScheduleStopper(scheduleStopper);

        Stoppable scheduleStopper1 = testModel.modelInstance.schedule.scheduleRepeating(targetAgent);
        this.targetAgent.setScheduleStopper(scheduleStopper1);

        // create instance of EatGrass Action
        this.reproduceWolf = new ReproduceWolf(0);
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
        // place currentAgent into the grid
        this.currentAgent.updateGridLocationTo(1, 1, false);

        // place tagetAgent in neighbouring cell
        this.targetAgent.updateGridLocationTo(0, 1, false);

        // get Neighbourhood of the currentAgent
        Neighbourhood neighbourhood = this.currentAgent.checkNeighbours();

        // execute the action
        this.reproduceWolf.execute(currentAgent, neighbourhood, this.testModel.modelInstance);

        Neighbourhood neighbourhoodAfterAction = this.currentAgent.checkNeighbours();
        Cell[] neighbourCells = neighbourhoodAfterAction.getAllNeighbours();
        Stream<Cell> neighbourCellsStream = Arrays.stream(neighbourCells);
        // TEST: the neighbourhood should contain a wolf agent, that is neither "targetAgent" nor "currentAgent"
        assertTrue(neighbourCellsStream.anyMatch(x -> x.getEntity() != targetAgent && x.getEntity() != currentAgent));

        // TEST: currentAgent shouldn't be able to reproduce
        assertTrue(!currentAgent.getCanReproduceAgain());

        // TEST: targetAgent shouldn't be able to reproduce
        assertTrue(!targetAgent.getCanReproduceAgain());


    }

    @Test
    public void testCheckCondition() throws GridLocationOccupiedException
    {

        // change fertility of both agents
        targetAgent.setCanReproduceAgain(true);
        currentAgent.setCanReproduceAgain(true);

        // place currentAgent into the grid
        this.currentAgent.updateGridLocationTo(1, 1, false);

        // get Neighbourhood
        Neighbourhood neighbourhood = this.currentAgent.checkNeighbours();

        System.out.println("Neighbourhood: " + neighbourhood.toString());

        // TEST: condition isn't fullfilled (no neighbouring sheep agent)
        assertTrue(!this.reproduceWolf.checkCondition(this.currentAgent, neighbourhood, this.testModel.modelInstance));

        // place tagetAgent in neighbouring cell
        this.targetAgent.updateGridLocationTo(0, 1, false);

        // re-check neighbourhood adding a wolf
        neighbourhood = this.currentAgent.checkNeighbours();

        System.out.println("Neighbourhood: " + neighbourhood.toString());

        // TEST: condition is fullfilled
        assertTrue(this.reproduceWolf.checkCondition(this.currentAgent, neighbourhood, this.testModel.modelInstance));

        // change fertility of both agents
        targetAgent.setCanReproduceAgain(false);
        currentAgent.setCanReproduceAgain(false);

        // TEST: reproduction should fail (currentAgent AND targetAgent aren't fertile)
        assertTrue(!this.reproduceWolf.checkCondition(this.currentAgent, neighbourhood, this.testModel.modelInstance));

        // make one Agent fertile
        targetAgent.setCanReproduceAgain(true);

        // TEST: reproduction should fail (currentAgent isn't fertile)
        assertTrue(!this.reproduceWolf.checkCondition(this.currentAgent, neighbourhood, this.testModel.modelInstance));

        // make other Agent fertile
        currentAgent.setCanReproduceAgain(true);
        targetAgent.setCanReproduceAgain(false);

        // TEST: reproduction should fail (targetAgent isn't fertile)
        assertTrue(!this.reproduceWolf.checkCondition(this.currentAgent, neighbourhood, this.testModel.modelInstance));

        // un-alive targetAgent
        targetAgent.die();

        // TEST: reproduction should fail, as the target agent is dead
        assertTrue(!this.reproduceWolf.checkCondition(this.currentAgent, neighbourhood, this.testModel.modelInstance));

    }
}

