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

package Model.Entities.Agents;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import org.junit.Before;
import org.junit.Test;

import Model.Entities.Objects.Grass;
import Model.Exceptions.GridLocationOccupiedException;
import Model.Neighbourhood.Cell;
import Model.Neighbourhood.Neighbourhood;

import ec.util.MersenneTwisterFast;
import sim.field.grid.ObjectGrid2D;
import sim.util.Int2D;
import utils.TestUtils;


import java.awt.Color;

public class AgentTest 
{
    private Agent agent;
    private ObjectGrid2D grid;

    @Before
    public void setUp() {

        MersenneTwisterFast rng = new MersenneTwisterFast();
        
        // create artificial , empty grid
        this.grid = new ObjectGrid2D(10, 10);

        // Initialize the agent instance before each test
        agent = new Wolf(20, grid, rng, 0); 
    }

    @Test
    public void testAgentColor() {
        assertEquals(Color.gray, agent.getColor());
    }

    @Test
    public void testCheckNeighbours() throws GridLocationOccupiedException
    {
        TestUtils.addGrassToGrid(this.grid);

        Wolf w2 = new Wolf(20, this.grid, null, 0);
        Sheep s1 = new Sheep(20, this.grid, null, 0);
        Wolf w3 = new Wolf(20, this.grid, null, 0);
        Sheep s2 = new Sheep(20, this.grid, null, 0);
        
        Int2D middle = new Int2D(1, 1);
        Int2D left = new Int2D(0, 1);
        Int2D top = new Int2D(1, 0);
        Int2D right = new Int2D(2, 1);
        Int2D bottom = new Int2D(1, 2);

        // fill grid with some agent combinations

        // >>>>> CASE 1: all neighbouring cells contain Entities >>>>>

        // place all agents on the grid
        agent.updateGridLocationTo(middle.getX(), middle.getY(), false);

        w3.updateGridLocationTo(top.getX(), top.getY(), false);
        s1.updateGridLocationTo(right.getX(), right.getY(), false);
        s2.updateGridLocationTo(bottom.getX(), bottom.getY(), false);
        w2.updateGridLocationTo(left.getX(), left.getY(), false);

        Neighbourhood neighbours = agent.checkNeighbours();

        // define how the correct Neighbourhood should look like
        Neighbourhood correctNeighbourhood = new Neighbourhood(new Cell(w3, top), new Cell(s1, right), new Cell(s2, bottom), new Cell(w2, left));

        assertEquals(correctNeighbourhood.getTop().getEntity(), neighbours.getTop().getEntity());
        assertEquals(correctNeighbourhood.getRight().getEntity(), neighbours.getRight().getEntity());
        assertEquals(correctNeighbourhood.getBottom().getEntity(), neighbours.getBottom().getEntity());
        assertEquals(correctNeighbourhood.getLeft().getEntity(), neighbours.getLeft().getEntity());

        // <<<<< CASE 1 <<<<<

        // >>>>> CASE 2: no neighbours >>>>>

        // empty the grid
        this.grid.clear();

        TestUtils.addGrassToGrid(this.grid);

        // place this agent again (it will be the only agent currently on the grid; the rest is "Grass")
        agent.updateGridLocationTo(middle.getX(), middle.getY(), false);

        // check Neighbourhood
        neighbours = agent.checkNeighbours();

        for (Cell n : neighbours.getAllNeighbours())
        {
            assertEquals(Grass.class, n.getEntity().getClass());
        }
        
        // <<<<< CASE 2 <<<<<

        // >>>>> CASE 3: some neighbouring cells are out of bounds >>>>>
        // empty the grid
        this.grid.clear();

        TestUtils.addGrassToGrid(this.grid);

        // CASE 3: some neighbouring cells are out of bounds
        middle = new Int2D(0,0);
        // top = null;
        right = new Int2D(1,0);
        bottom = new Int2D(0,1);
        // left = null;


        // place agents on the grid
        agent.updateGridLocationTo(middle.getX(), middle.getY(), false);
        w2.updateGridLocationTo(right.getX(), right.getY(), false);
        s1.updateGridLocationTo(bottom.getX(), bottom.getY(), false);

        // define how the correct Neighbourhood should look like
        correctNeighbourhood = new Neighbourhood(null, new Cell(w2, right), new Cell(s1, bottom), null);

        // check Neighbourhood
        neighbours = agent.checkNeighbours();

        for (Cell n : neighbours.getAllNeighbours())
        {
            if (n != null) System.out.println(n.getEntity().getClass().getSimpleName() + "@" + System.identityHashCode(this.getClass()));
        }

        assertEquals(correctNeighbourhood.getTop(), neighbours.getTop());
        assertEquals(correctNeighbourhood.getRight().getEntity(), neighbours.getRight().getEntity());
        assertEquals(correctNeighbourhood.getBottom().getEntity(), neighbours.getBottom().getEntity());
        assertEquals(correctNeighbourhood.getLeft(), neighbours.getLeft());

        // <<<<< CASE 3 <<<<<

        // check neighbourhood position
        assertTrue("Neighbour from the wrong grid position was choosen!", neighbours.getBottom().getLocation().getX() == bottom.getX() && neighbours.getBottom().getLocation().getY() == bottom.getY());

    }
}
