package starwars.actions;

import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.actors.SWOwnerInterface;

public class InterstellarTravelToTatooine extends SWAffordance {

	
	public InterstellarTravelToTatooine(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor actor) {
		// TODO Auto-generated method stub
		return actor.hasCapability(Capability.PILOT) && actor.hasCapability(Capability.FLYTOTATOOINE);
	}

	@Override
	public void act(SWActor actor) {
		// TODO Auto-generated method stub
		
		actor.world.setLocation(actor, 0);
		actor.removeCapability(Capability.FLYTOTATOOINE);
		
		if(!actor.hasCapability(Capability.FLYTODEATHSTAR)){
			actor.addCapability(Capability.FLYTODEATHSTAR);
		}
		if(!actor.hasCapability(Capability.FLYTOYAVIN)){
			actor.addCapability(Capability.FLYTOYAVIN);
		}
		
		
	}

    @Override
    public String getDescription() {
	return "Fly to Tatooine";
    }

	
}
