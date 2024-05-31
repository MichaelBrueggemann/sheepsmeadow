package Model.Entities.Agents.Behavior.Actions;

import java.util.Comparator;

public class GeneralActionComparator implements Comparator<GeneralAction>{
    
    /**
     * Compare two "GeneralAction"s by their Priority.
     */
    public int compare(GeneralAction a, GeneralAction b) {
        return Integer.compare(a.getPriority(), b.getPriority());
    }
}
