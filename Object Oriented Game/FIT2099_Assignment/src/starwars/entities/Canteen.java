package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Drink;
import starwars.actions.Take;

/**
 * A canteen that can be used to contain water.
 * 
 * It can be filled at a Reservoir, or any other Entity
 * that has a Dip affordance.
 * 
 * @author Robert Merkel
 * 
 */
public class Canteen extends SWEntity implements Fillable {

    private int capacity;
    private int level;

    public Canteen(MessageRenderer m, int capacity, int initialLevel) {
	super(m);
	this.shortDescription = "a canteen";
	this.longDescription = "a slightly battered aluminium canteen";
	this.setSymbol("o");
	this.setMaxAndCurrentHitPoints(10);

	this.capacity = capacity;
	this.level = initialLevel;
	addCapability(Capability.FILLABLE);

	this.addAffordance(new Drink(this, m));
	this.addAffordance(new Take(this, m));
    }

    @Override
    public void fill() {
	level = capacity;
    }

    @Override
    public void drink() {
	level--;
    }

    @Override
    public Capability filledWith() {
	if (level > 0)
	    return Capability.WATER_BASED;
	else
	    return Capability.NONE;
    }

    @Override
    public String getShortDescription() {
	return shortDescription + " [" + level + "/" + capacity + "]";
    }

    @Override
    public String getLongDescription() {
	return longDescription + " [" + level + "/" + capacity + "]";
    }

    @Override
    public void takeDamage(int hitPoints) {
	super.takeDamage(hitPoints);
	if (isDead()) {
	    removeCapability(Capability.FILLABLE);
	    removeAffordance(Drink.class);
	    level = 0;
	    capacity = 0;
	    shortDescription = "a broken canteen";
	    longDescription = "an extremely battered aluminium canteen";
	}
    }
}
