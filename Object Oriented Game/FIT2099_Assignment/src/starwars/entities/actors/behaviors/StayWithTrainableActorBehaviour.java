package starwars.entities.actors.behaviors;

import starwars.Capability;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Speak;

public class StayWithTrainableActorBehaviour extends BehaviourInterface {

    public StayWithTrainableActorBehaviour(SWActor actor, SWWorld world) {
	super(actor, world);
	// TODO Auto-generated constructor stub
    }

    @Override
    public boolean ExecuteBehaviour() {
	if(getLocalEntityWithCapability(Capability.JEDI_STUDENT) != null)
	{
	    actor.schedule(new Speak(messageRenderer, "I can train you in the ways of The Force."));
	    return true;
	}
	return false;
    }

}
