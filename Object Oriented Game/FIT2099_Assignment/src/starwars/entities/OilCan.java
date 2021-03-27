package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.*;

/**
 * A can of oil that can be used to heal droids.
 * 
 */
public class OilCan extends SWEntity implements Fillable {

    public OilCan(MessageRenderer m) {
	super(m);
	this.shortDescription = "an oil can";
	this.longDescription = "a can of Droid-Fix brand oil.";
	this.setSymbol("x");
	this.setMaxAndCurrentHitPoints(10);

	this.addAffordance(new Drink(this, m));
	this.addAffordance(new Take(this, m));
	this.addCapability(Capability.DROID_REPAIR);
    }

    @Override
    public void fill() {
    }

    @Override
    public void drink() {
    }

    @Override
    public Capability filledWith() {
	return Capability.OIL_BASED;
    }

    @Override
    public void takeDamage(int hitPoints) {
	super.takeDamage(hitPoints);
	if (isDead()) {
	    removeAffordance(Drink.class);
	    shortDescription = "a broken oil can";
	    longDescription = "a broken can of Droid-Fix brand oil";
	}
    }
}
