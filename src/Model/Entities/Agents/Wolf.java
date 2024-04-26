package Model.Entities.Agents;

import java.awt.Color;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import Model.Entities.Agents.Behavior.Actions.GeneralActionComparator;
import Model.Entities.Agents.Behavior.Actions.DefaultMove;
import Model.Entities.Agents.Behavior.Actions.GeneralAction;
import Model.Entities.Objects.Grass;

import sim.engine.SimState;
import sim.field.grid.ObjectGrid2D;



public class Wolf extends Agent
{

    public Wolf(int id, int energy, ObjectGrid2D grid)
    {

        // construct all attributes of an "Agent"
        super(id, Color.gray, energy, grid);

        // Comparator needed to decide the order of two "GeneralAction"s
        Comparator<GeneralAction> generalActionComparator = new GeneralActionComparator();

        // create "Action"s for this agents "ActionList"
        GeneralAction defaultMove = new DefaultMove(0);
        // ..

        this.ruleSet = new PriorityQueue<>(generalActionComparator);
        ruleSet.add(defaultMove);

        // // store all PQs in a HashMap
        // HashMap<AgentType, PriorityQueue<GeneralAction>> wolfActionMap = new HashMap<>();

        // // add all Sheep-based "Action"s
        // wolfActionMap.put(AgentType.SHEEP, sheepBasedActions);

        // // add all Wolf-based "Action"s
        // wolfActionMap.put(AgentType.WOLF, wolfBasedActions);

        // // add all Grass-based "Action"s
        // wolfActionMap.put(AgentType.GRASS, grassBasedActions);

        


        this.addPriorityClass(0, Sheep.class); // priority 0
        this.addPriorityClass(1, Wolf.class); // priority 1
        this.addPriorityClass(2, Grass.class); // priority 2
    }

    @Override
    public void step(SimState state)
    {

    }
}