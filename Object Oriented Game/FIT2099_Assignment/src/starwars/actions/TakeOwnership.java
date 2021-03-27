package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.entities.actors.PrincessLeiaOrgana;
import starwars.entities.actors.SWOwnerInterface;

public class TakeOwnership extends SWAffordance {

    public TakeOwnership(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
    }

    @Override
    public boolean canDo(SWActor actor) {
	assert(target instanceof SWOwnerInterface);
	SWOwnerInterface aim = (SWOwnerInterface)target;
	return !aim.hasOwner();
    }

    @Override
    public void act(SWActor actor) {
    if(target instanceof PrincessLeiaOrgana){
    	actor.say(String.format("%s is now following %s",target.getShortDescription(), actor.getShortDescription()));
    }
    else{
    	actor.say(String.format("%s owns %s now.", actor.getShortDescription(), target.getShortDescription()));
    }
	((SWOwnerInterface)target).setOwner(actor);
	
    }

    @Override
    public String getDescription() {
    	if(target instanceof PrincessLeiaOrgana){
    		return "Let " + target.getShortDescription() + " follow you";
    	}
	return "Take ownership of " + target.getShortDescription();
    }
}
