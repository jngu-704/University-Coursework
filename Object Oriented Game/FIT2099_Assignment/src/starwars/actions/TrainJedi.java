package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

public class TrainJedi extends SWAffordance {

    public TrainJedi(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
    }

    @Override
    public boolean canDo(SWActor actor) {
	return actor.hasCapability(Capability.JEDI_STUDENT);
    }

    @Override
    public void act(SWActor actor) {
	if(actor.getAffordance(MindControl.class) != null)
	{
	    actor.removeAffordance(MindControl.class);
	    actor.say(String.format("%s can no longer be tricked so easily.", actor.getShortDescription()));
	    return;
	}
	    
	if(!actor.hasCapability(Capability.WIELDS_LIGHTSABER))
	{
	    actor.addCapability(Capability.WIELDS_LIGHTSABER);
	    actor.say(String.format("%s learns an elegant weapon from a more civilized age.", actor.getShortDescription()));
	    return;
	} 
	    
	if(!actor.hasCapability(Capability.MIND_CONTROLLER))
	{
	    actor.addCapability(Capability.MIND_CONTROLLER);
	    actor.removeCapability(Capability.JEDI_STUDENT);
	    actor.say(String.format("%s can control the minds of the weak willed.", actor.getShortDescription()));
	    actor.say(String.format("%s has completed his training.", actor.getShortDescription()));
	    return;
	} 
    }

    @Override
    public String getDescription() {
	return target.getShortDescription() + " will train you in the way of The Force";
    }
}
