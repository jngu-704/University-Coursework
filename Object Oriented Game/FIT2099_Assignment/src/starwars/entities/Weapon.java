package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Attack;
import starwars.actions.Take;

public class Weapon extends SWEntity {

    public Weapon(MessageRenderer m, int hitPoints) {
	super(m);

	this.setMaxAndCurrentHitPoints(hitPoints);
	this.addCapability(Capability.WEAPON); // You can hit someone with it.
	this.addAffordance(new Take(this, m)); // Add the take affordance so that the LightSaber can be taken by SWActors
	this.addAffordance(new Attack(this, m)); // So you can hit it. Raider smash!
    }

    @Override
    public void takeDamage(int hitPoints) {
	super.takeDamage(hitPoints);

	if (this.isDead()) {
	    this.removeCapability(Capability.WEAPON);
	    this.removeAffordance(Attack.class);
	}
    }
}
