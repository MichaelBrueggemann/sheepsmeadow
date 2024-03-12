package Model.Entities.Objects;

import java.awt.Color;

import Model.Entities.Entity;
import sim.engine.SimState;

public class Grass extends Entity
{
    // time span until grass is "edible" again
    private int regroth_period = 3;

    // status flag, if grass was "eaten" by a sheep
    private boolean regrowing;

    public Grass(int id)
    {
        super(id, Color.green);
        this.regrowing = false;
    }

    @Override
    public void step(SimState state)
    {

    }
}
