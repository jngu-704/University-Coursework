package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.*;
import starwars.entities.actors.behaviors.*;

public class SWDroidActor extends SWActor implements SWOwnerInterface {

    protected FollowBehaviour followBehaviour;

    public SWDroidActor(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
	super(team, hitpoints, m, world);
	this.addCapability(Capability.OIL_BASED);
	addAffordance(new TakeOwnership(this, messageRenderer));
	addAffordance(new Repair(this, messageRenderer));

	followBehaviour = new FollowBehaviour(this, world, null);
    }

    @Override
    public boolean hasOwner() {
	return followBehaviour.hasOwner();
    }

    @Override
    public void setOwner(SWActor newOwner) {
	followBehaviour.setOwner(newOwner);
    }

    @Override
    public void takeDamage(int hitPoints) {
	super.takeDamage(hitPoints);
	if (isDead()) {
	    addAffordance(new DismantleDroid(this, messageRenderer));
	    addAffordance(new RebuildDroid(this, messageRenderer));
	}
    }

    @Override
    public void heal(int hitPoints)
    {
	boolean wasDead = isDead();
	super.heal(hitPoints);
	if(wasDead && !isDead()){
	    removeAffordance(DismantleDroid.class);
	    removeAffordance(RebuildDroid.class);
	}
    }
    
    @Override
    public void movedToLocation(SWLocation loc) {
	if(loc.getSymbol() == 'b'){ //This is the only defining characterist of the badlands.
	    takeDamage(5);
	}
    }
}
