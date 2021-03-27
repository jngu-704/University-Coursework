package starwars.entities.actors.behaviors;

import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.*;

public class PickUpItem extends BehaviourInterface {

    private Class<? extends SWEntityInterface> itemType;

    public PickUpItem(SWActor actor, SWWorld world, Class<? extends SWEntityInterface> itemType) {
	super(actor, world);
	this.itemType = itemType;
    }

    @Override
    public boolean ExecuteBehaviour() {
	SWEntityInterface item = getLocalTakeableEntityOfType(itemType);

	if (actor.getItemCarried() != null && actor.getItemCarried().getClass() == itemType)
	    return false;

	if (item != null) {
	    if (actor.getItemCarried() != null)
		actor.schedule(actor.getItemCarried().getAffordance(Leave.class));
	    else
		actor.schedule(item.getAffordance(Take.class));
	    return true;
	}

	return false;
    }

}
