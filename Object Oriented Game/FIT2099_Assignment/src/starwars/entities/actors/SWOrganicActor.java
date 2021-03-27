package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;

public abstract class SWOrganicActor extends SWActor {

    public SWOrganicActor(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
	super(team, hitpoints, m, world);
	this.addAffordance(new MindControl(this, world, m));
	this.addCapability(Capability.WATER_BASED);
    }

}
