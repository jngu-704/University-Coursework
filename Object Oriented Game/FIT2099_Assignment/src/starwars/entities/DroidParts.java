package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.actions.Take;

public class DroidParts extends SWEntity {

    public DroidParts(MessageRenderer m) {
	super(m);
	this.shortDescription = "droid parts";
	this.longDescription = "the remains of a broken droid";
	this.setSymbol("p");
	this.addAffordance(new Take(this, m));
    }
    
    public boolean canBeUsedBy(SWActor user) {
	return user.hasCapability(Capability.DROID_MECHANIC);
    }
}
