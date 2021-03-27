package starwars.actions;

import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.actors.SWOwnerInterface;

public class InterstellarTravelToYavin extends SWAffordance {
	
	
	public InterstellarTravelToYavin(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor actor) {
		// TODO Auto-generated method stub
		return actor.hasCapability(Capability.PILOT) && actor.hasCapability(Capability.FLYTOYAVIN);
	}

	@Override
	public void act(SWActor actor) {
		// TODO Auto-generated method stub
		
		actor.world.setLocation(actor, 2);
		actor.removeCapability(Capability.FLYTOYAVIN);
		
		if(!actor.hasCapability(Capability.FLYTOTATOOINE)){
			actor.addCapability(Capability.FLYTOTATOOINE);
		}
		if(!actor.hasCapability(Capability.FLYTODEATHSTAR)){
			actor.addCapability(Capability.FLYTODEATHSTAR);
		}
		
	}

    @Override
    public String getDescription() {
	return "Fly to Yavin IV";
    }

	
}
