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

package Model;

import sim.engine.*;
import sim.field.grid.ObjectGrid2D;
import sim.util.Bag;
import Model.Entities.*;
import Model.Entities.Agents.*;
import Model.Entities.Objects.Grass;
import Model.Exceptions.GridLocationOccupiedException;

import java.beans.PropertyChangeSupport;
import java.util.stream.Stream;
import java.beans.PropertyChangeListener;

public class Model extends SimState 
{
    // ===== ATTRIBUTES =====

    // field enable adding listeners to the other model fields
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    // create spatial representation for the model (a "field"). This is where all agents "live"
    private ObjectGrid2D meadow = new ObjectGrid2D(1, 1);

    // sets number of sheeps in this simulation
    private int sheeps;

    // sets number of wolves in this simulation
    private int wolves;

    // Boundary for maximum number of Agents
    private int MAX_INDIVIDUALS;

    private double wolfFertilityRate;

    private double sheepFertilityRate;

    // size of the ObjectGrid2D. The grid will always have the same width and height
    private int gridSize = 1;

    // defines, how many steps an agent has to wait, until it can reproduce again
    private int reproductionDelay;

    public Model(long seed)
    {   
        super(seed);
        this.MAX_INDIVIDUALS = this.meadow.getHeight() * this.meadow.getWidth();
    }

    // ===== METHODS =====
    

    // ===== MASON CONTROL METHODS =====

    /**
     * This function is run once on model start up. Here you can:
     * - populate the grid with Agents
     * - 
     */ 
    public void start()
    {
        // call implementaion of super method
        super.start();

        // clear spatial representation of the model
        meadow.clear();

        // populate meadow with agents
        this.populateMeadow();
    }


    // ===== HELPER METHODS =====
    /**
     * Utitlity function to make writing these tests easier
     */
    public void addGrassToGrid()
    {
        ObjectGrid2D grid = this.meadow;

        // initialize each cell of the grid with a Grass object
        for (int i = 0; i < grid.getHeight(); i++)
        {
            for (int j = 0; j < grid.getWidth(); j++)
            {
                Grass grass = new Grass(null);

                grass.addToLocation(grid, i, j);
            }
        }
    }


    /**
     * Populates the grid with Agents. Each Grid cell will be filled with an Entity.
     */
    public void populateMeadow()
    {
        this.addGrassToGrid();
        
        // counters to keep track of the number of already added agents
        int sheep_counter = 0;
        int wolf_counter = 0;

        int num_individuals = this.wolves + this.sheeps;

        while (sheep_counter + wolf_counter != num_individuals)

        {
            // Add sheeps
            if (sheep_counter < this.sheeps)
            {
                Sheep sheep = new Sheep(20, this.meadow, this.random, this.reproductionDelay);

                // find a random, empty cell in the grid
                while (true) 
                {
                    // draw random int from 0 till gridsize
                    int x = random.nextInt(meadow.getWidth());
                    int y = random.nextInt(meadow.getHeight());
                    
                    // get current Entity at x,y
                    Entity entity = (Entity) meadow.get(x,y);

                    // if cell is "empty" (only a grass object is present)
                    if (entity instanceof Grass)
                    {
                        // update agent location
                        try {
                            sheep.updateGridLocationTo(x, y, false);
                        } catch (GridLocationOccupiedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    }
                }

                // add Entity to the schedule
                Stoppable scheduleStopper = this.schedule.scheduleRepeating(sheep);
                sheep.setScheduleStopper(scheduleStopper);
                
                sheep_counter++;
            }

            // Add wolves
            if (wolf_counter < this.wolves)
            {
                Wolf wolf = new Wolf(20, this.meadow, this.random, this.reproductionDelay);

                // find a random, empty cell in the grid
                while (true) 
                {
                    // draw random int from 0 till gridsize
                    int x = random.nextInt(meadow.getWidth());
                    int y = random.nextInt(meadow.getHeight());

                    // get current Entity at x,y
                    Entity entity = (Entity) meadow.get(x,y);

                    // if cell is "empty" (only a grass object is present)
                    if (entity instanceof Grass)
                    {
                        // update agent location
                        try {
                            wolf.updateGridLocationTo(x, y, false);
                        } catch (GridLocationOccupiedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    }
                }

                // add Entity to the schedule
                Stoppable scheduleStopper = this.schedule.scheduleRepeating(wolf);
                wolf.setScheduleStopper(scheduleStopper);
                
                wolf_counter++;

            }
        }
    }


    // ===== GETTER & SETTER =====

    public int getSheeps() 
    {
        return this.sheeps;
    }

    public void setSheeps(int value) throws IllegalArgumentException, Exception{

        if (value < 0) throw new IllegalArgumentException("Value has to be greater than 0!");

        if (this.wolves + value > this.MAX_INDIVIDUALS) throw new Exception("Too much agents");
        
        this.sheeps = value;
    }

    public int getWolves() 
    {
        return this.wolves;
    }

    public void setWolves(int value) throws IllegalArgumentException, Exception
    {

        if (value < 0) throw new IllegalArgumentException("Value has to be greater than 0!");

        if (this.sheeps + value > this.MAX_INDIVIDUALS) throw new Exception("Too much agents");

        this.wolves = value;
    }

    public int[] getSheepEnergyDistribution()
    {

        // get all sheeps currently on the Meadow
        Stream<Object> stream = this.meadow.elements().stream();

        Object[] sheeps = stream.filter(obj -> obj instanceof Sheep).toArray();

        int num_sheeps = sheeps.length;

        int[] sheepEnergyDistribution = new int[num_sheeps];

        for (int i = 0; i < num_sheeps; ++i)
        {
            sheepEnergyDistribution[i] = ((Sheep) sheeps[i]).getEnergy();
        }

        return sheepEnergyDistribution;
    }

    public int[] getWolfEnergyDistribution()
    {

        // get all sheeps currently on the Meadow
        Stream<Object> stream = this.meadow.elements().stream();

        Object[] wolves = stream.filter(obj -> obj instanceof Wolf).toArray();

        int num_wolves = wolves.length;

        int[] wolfEnergyDistribution = new int[num_wolves];

        for (int i = 0; i < num_wolves; ++i)
        {
            wolfEnergyDistribution[i] = ((Wolf) wolves[i]).getEnergy();
        }

        return wolfEnergyDistribution;
    }

    /** 
     * This getter has to be named breaking the convention, to prevent the Model Inspector from "MeadowDisplay.java" to draw it in the "Model" tab
     */
    public ObjectGrid2D returnMeadow() 
    {
        return this.meadow;
    }

    public int returnMAX_INDIVIDUALS()
    {
        return this.MAX_INDIVIDUALS;
    }


    public double getWolfFertilityRate() 
    {
        return this.wolfFertilityRate;
    }
      
    public void setWolfFertilityRate(double value) 
    {
        if (value >= 0.0d && value <= 1.0d) this.wolfFertilityRate = value;
    }
  
    public Object domWolfFertilityRate()
    {
        return new sim.util.Interval(0.0d, 1.0d);
    }

    public double getSheepFertilityRate() 
    {
        return this.sheepFertilityRate;
    }

    public void setSheepFertilityRate(double value) 
    {
        if (value >= 0.0d && value <= 1.0d ) this.sheepFertilityRate = value;
    }
    
    public Object domSheepFertilityRate()
    {
        return new sim.util.Interval(0.0d, 1.0d);
    }

    public int getReproductionDelay() 
    {
      return this.reproductionDelay;
    }

    public void setReproductionDelay(int value) 
    {
      this.reproductionDelay = value;
    }

    public int getGridSize()
    {
        return this.gridSize;
    }

    public void setGridSize(int value) 
    {
        this.gridSize = value;

        // transform the grid
        this.meadow.reshape(value, value);

        // reset wolves and sheep
        int oldWolves = this.wolves;
        int oldSheeps = this.sheeps;
        this.wolves = 0;
        this.sheeps = 0;

        // update MAX_INDIVIDUALS
        this.MAX_INDIVIDUALS = this.meadow.getWidth() * this.meadow.getHeight();
    
        propertyChangeSupport.firePropertyChange("wolves", oldWolves, this.wolves);
        propertyChangeSupport.firePropertyChange("sheeps", oldSheeps, this.sheeps);
    }


    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    // ===== MAIN =====

    public static void main(String[] args)
    {
        // run the simulation with given model class file and any arguments given from the terminal
        doLoop(Model.class, args);

        // exit main process to ensure all threads have stopped
        System.exit(0); 
    }

    
}
