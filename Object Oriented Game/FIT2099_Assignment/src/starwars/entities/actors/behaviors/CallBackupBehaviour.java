package starwars.entities.actors.behaviors;


import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.CallBackup;
import starwars.entities.actors.StormTrooper;
import starwars.swinterfaces.SWGridController;
public class CallBackupBehaviour extends BehaviourInterface {
    /**
     * Constructor for <code>CallBackupBehaviour</code> class. Will initialize the callBackup for the actor and the world for the <code>CallBackBehaviour</code>.
     * 
     * @param actor
     * 			   The actor to perform the behaviour for
     * @param world
     * 			   The world to perform the behaviour for
     */
	public CallBackupBehaviour(SWActor actor, SWWorld world) {
		super(actor, world);
		// TODO Auto-generated constructor stub
	}

	
    /**
     * Perform the <code>CallBackupBehaviour</code> Behaviour.
     * <p>
	 * Checks if the current space is empty then checks if the randomized number is less than 0.05 then it will call the CallBackup Action
     * <p>
     */
	
	@Override
	public boolean ExecuteBehaviour() {

		SWLocation location = actor.world.getEntityManager().whereIs(actor);
		List<SWEntityInterface> contents = actor.world.getEntityManager().contents(location);
		double x = Math.random();

		if (contents.size() == 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report

			if(x<0.05){
				actor.schedule(new CallBackup(messageRenderer));

			}
		
	}
		
		return false;
	}

}
