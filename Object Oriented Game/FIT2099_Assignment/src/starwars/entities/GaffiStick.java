package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

public class GaffiStick extends Weapon {

    public GaffiStick(MessageRenderer m) {
	super(m, 20);

	this.shortDescription = "a gaffi stick";
	this.longDescription = "A gaffi stick, the traditional weapon of the Tusken raiders";
    }

    @Override
    public String getSymbol() {
	return "/";
    }

    @Override
    public void takeDamage(int hitPoints) {
	super.takeDamage(hitPoints);

	if (this.isDead()) {
	    this.shortDescription = "a broken gaffi stick";
	    this.longDescription = "A broken gaffi stick";
	}
    }
}
