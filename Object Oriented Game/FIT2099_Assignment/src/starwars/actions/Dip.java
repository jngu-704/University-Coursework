package starwars.actions;

import starwars.SWAffordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.entities.Fillable;

/**
 * An Affordance for dipping things into large bodies of liquid.
 * 
 * The affordance is offered by the large bodies of liquid and can only be
 * applied in partnership with an <code>Entity</code> that is  <code>Fillable</code>
 * and implements the <code>Fill</code> interface.
 * 
 * @author Robert Merkel
 * @see {@link starwars.entities.Fillable}
 */
public class Dip extends SWAffordance implements SWActionInterface {

    public Dip(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
	priority = 1;
    }

    @Override
    public boolean canDo(SWActor actor) {
	SWEntityInterface item = actor.getItemCarried();
	if (item != null) {
	    return item.hasCapability(Capability.FILLABLE);
	}
	return false;
    }

    @Override
    public void act(SWActor a) {
	SWEntityInterface item = a.getItemCarried();
	assert (item instanceof Fillable);
	((Fillable) item).fill();
	a.say(item.getShortDescription() + " has been refilled to capacity");
    }

    @Override
    public String getDescription() {
	return "dip carried item in " + target.getShortDescription();
    }
}
