package starwars.entities.actors.behaviors;

import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;

public class TrainerBehaviour extends BehaviourInterface {

    public TrainerBehaviour(SWActor actor, SWWorld world) {
	super(actor, world);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean ExecuteBehaviour() {
	for (SWEntityInterface entity : getLocalEntites())
	if(entity.hasCapability(Capability.JEDI_STUDENT)){
	    actor.say(String.format("%s says, I can train you in the way of The Force.", actor.getShortDescription()));
	    return true;
	}
	return false;
    }

}
