package starwars.entities.actors.behaviors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.Drink;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.entities.Fillable;

public class HealBehaviour extends BehaviourInterface {

    private MessageRenderer messageRenderer;
    private Capability oilOrWater;
    private SWEntity droppedItem;

    public HealBehaviour(SWActor actor, SWWorld world, Capability oilOrWater) {
	super(actor, world);
	this.messageRenderer = world.getMessageRenderer();
	this.oilOrWater = oilOrWater;
    }

    @Override
    public boolean ExecuteBehaviour() {
	boolean actorInjured = actor.getHitPoints() < actor.getMaxHitPoints();
	boolean carryingBottle = getDrinkableBottleInHand() != null;
	boolean nearBottle = getDrinkableGroundBottle() != null;

	if (actorInjured && carryingBottle) {
	    actor.schedule(new Drink(getDrinkableBottleInHand(), messageRenderer));
	    return true;
	}

	if (actorInjured && nearBottle) {
	    if (actor.getItemCarried() != null) {
		droppedItem = actor.getItemCarried();
		actor.schedule(droppedItem.getAffordance(Leave.class));
		return true;
	    } else {
		actor.schedule(((SWEntity) getDrinkableGroundBottle()).getAffordance(Take.class));
		return true;
	    }
	}

	if (droppedItem != null) {
	    if (actor.getItemCarried() != null) {
		actor.schedule(actor.getItemCarried().getAffordance(Leave.class));
		return true;
	    } else if (getLocalEntites().contains(droppedItem)) {
		actor.schedule((droppedItem).getAffordance(Take.class));
		droppedItem = null;
		return true;
	    }
	    droppedItem = null;
	}

	return false;
    }

    private Fillable getDrinkableGroundBottle() {
	for (SWEntityInterface item : getLocalEntites()) {
	    if (GoodBottle(item))
		return (Fillable) item;
	}
	return null;
    }

    private Fillable getDrinkableBottleInHand() {
	if (GoodBottle(actor.getItemCarried())){
	    return (Fillable)actor.getItemCarried();
	}
	return null;
    }

    private boolean GoodBottle(SWEntityInterface item) {
	if (item instanceof Fillable) {
	    Fillable bottle = (Fillable) item;
	    return bottle.filledWith() == oilOrWater;
	}
	return false;
    }

}
