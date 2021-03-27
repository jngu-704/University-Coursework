package starwars.actions;

import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.actors.SWOwnerInterface;

public class InterstellarTravelToDeathStar extends SWAffordance {
	
	
	public InterstellarTravelToDeathStar(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor actor) {
		// TODO Auto-generated method stub
		return actor.hasCapability(Capability.PILOT) && actor.hasCapability(Capability.FLYTODEATHSTAR);
	}

	@Override
	public void act(SWActor actor) {
		// TODO Auto-generated method stub
		
		actor.world.setLocation(actor, 1);
		actor.removeCapability(Capability.FLYTODEATHSTAR);

		if(!actor.hasCapability(Capability.FLYTOTATOOINE)){
			actor.addCapability(Capability.FLYTOTATOOINE);
		}
		if(!actor.hasCapability(Capability.FLYTOYAVIN)){
			actor.addCapability(Capability.FLYTOYAVIN);
		}
		
	}

    @Override
    public String getDescription() {
	return "Fly to the Death Star";
    }

	
}
