package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;

public class Drink extends SWAffordance {

    private Fillable bottle;

    public Drink(Fillable bottle, MessageRenderer m) {
	super((SWEntityInterface)bottle, m);
	this.bottle = bottle;
    }

    
    @Override
    /**
     * Needs healing, the bottle is filled with the thing that heals you. and it must be carried by you.
     */
    public boolean canDo(SWActor actor) {
	return (actor.getHitPoints() < actor.getMaxHitPoints()) && actor.hasCapability(bottle.filledWith()) && actor.getItemCarried() == bottle;
    }

    @Override
    public void act(SWActor actor) {
	actor.heal(10);
	((Fillable) target).drink();
	actor.say(String.format("%s drinks from %s", actor.getShortDescription(), target.getShortDescription()));

    }

    @Override
    public String getDescription() {
	return String.format("Drink from %s", target.getShortDescription());
    }

}
