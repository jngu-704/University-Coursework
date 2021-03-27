package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;

public class Repair extends SWAffordance {

    public Repair(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
    }

    @Override
    public boolean canDo(SWActor actor) {
	boolean canRepair = actor.hasCapability(Capability.DROID_REPAIR)
		|| (actor.getItemCarried() != null && actor.getItemCarried().hasCapability(Capability.DROID_REPAIR));
	return getTarget().getHitPoints() < getTarget().getMaxHitPoints() && !getTarget().isDead() && canRepair;
    }

    @Override
    public void act(SWActor actor) {
	getTarget().heal(20);
	if (actor.hasCapability(Capability.DROID_REPAIR)) {
	    actor.say(String.format("%s repairs %s.", actor.getShortDescription(), target.getShortDescription()));
	    return;
	}

	if (actor.getItemCarried() instanceof Fillable) { //TODO Some kind of Use method/interface? Why would this class think the repair item was Fillable?
	    actor.say(String.format("%s drinks from the %s.", actor.getShortDescription(), target.getShortDescription()));
	    ((Fillable) actor.getItemCarried()).drink();
	}
    }

    @Override
    public String getDescription() {
	return "Repair " + target.getShortDescription();
    }
}
