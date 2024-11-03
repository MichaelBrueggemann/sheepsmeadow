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

package Model.Entities.Agents.Behavior.Actions;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Model.Entities.Agents.Behavior.Actions.ConcreteActions.DefaultMove;
import Model.Entities.Agents.Behavior.Actions.ConcreteActions.EatGrass;

public class GeneralActionComparatorTest {
    
    @Test
    public void testGeneralActionComparator()
    {
        // create comparator
        GeneralActionComparator generalActionComparator = new GeneralActionComparator();

        // create two GeneralActions
        GeneralAction eatGrass = new EatGrass(0);
        GeneralAction defaultMove = new DefaultMove(1);

        // get comparison result
        int result = generalActionComparator.compare(eatGrass, defaultMove);

        // TEST: result should be negative, as "eatGrass" has a smaller priority number than "defaultMove"
        assertTrue(result < 0);

        result = generalActionComparator.compare(defaultMove, eatGrass);

        // TEST: result should be positive, as "defaultMove" has a higher priority number than "eatGrass"
        assertTrue(result > 0);


        GeneralAction defaultMove2 = new DefaultMove(0);

        result = generalActionComparator.compare(eatGrass, defaultMove2);

        // TEST: result should be equal to 0. as "defaultMove2" and "eatGrass" have the same priority
        assertTrue(result == 0);
    }
}
