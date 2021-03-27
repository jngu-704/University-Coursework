package starwars.entities.actors.behaviors;

import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.*;
import starwars.entities.DroidParts;

public class RepairBehaviour extends BehaviourInterface {

    public RepairBehaviour(SWActor actor, SWWorld world) {
	super(actor, world);
    }

    @Override
    public boolean ExecuteBehaviour() {
	boolean hasDroidParts = actor.getItemCarried() instanceof DroidParts;
	boolean nearDroidParts = getLocalEntityOfType(DroidParts.class) != null;
	SWEntityInterface dismantleableDroid = getLocalEntityWithAffordance(DismantleDroid.class);
	SWEntityInterface rebuildableDroid = getLocalEntityWithAffordance(RebuildDroid.class);
	SWEntityInterface repairableDroid = getLocalEntityWithAffordance(Repair.class);

	if (!hasDroidParts && nearDroidParts) {
	    if (actor.getItemCarried() != null)
		actor.schedule(actor.getItemCarried().getAffordance(Leave.class));
	    else
		actor.schedule(getLocalEntityOfType(DroidParts.class).getAffordance(Take.class));
	    return true;
	}

	if (hasDroidParts && rebuildableDroid != null) {
	    actor.schedule(rebuildableDroid.getAffordance(RebuildDroid.class));
	    return true;
	}

	if (!hasDroidParts && dismantleableDroid != null) {
	    actor.schedule(dismantleableDroid.getAffordance(DismantleDroid.class));
	    return true;
	}

	if (repairableDroid != null) {
	    actor.schedule(repairableDroid.getAffordance(Repair.class));
	    return true;
	}

	return false;
    }
}
