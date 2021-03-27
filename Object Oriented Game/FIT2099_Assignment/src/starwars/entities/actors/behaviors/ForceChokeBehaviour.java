package starwars.entities.actors.behaviors;

import java.util.ArrayList;

import starwars.ARandom;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Attack;
import starwars.actions.ForceChoke;

public class ForceChokeBehaviour extends BehaviourInterface  {
    /**
     * Constructor for <code>ForceChokeBehaviour</code> class. Will initialize the ForceChoke for the actor and the world for the <code>ForceChkeBehaviour</code>.
     * 
     * @param actor
     * 			   The actor to perform the behaviour for
     * @param world
     * 			   The world to perform the behaviour for
     */
	public ForceChokeBehaviour(SWActor actor, SWWorld world) {
		super(actor, world);
		// TODO Auto-generated constructor stub
	}
	
	
    /**
     * Perform the <code>ForceChokeBehaviour</code> Behaviour.
     * <p>
	 * Checks if there are other entities on the same spot and performs the ForceChoke action on the entities
     * <p>
     */
	@Override
	public boolean ExecuteBehaviour() {

		ArrayList<SWEntityInterface> targets = new ArrayList<SWEntityInterface>();

		for (SWEntityInterface target : getLocalEntites()) {
		    if (target == actor) // Why are you hitting yourself?
			continue;

		    if (target.isDead()) // Don't beat a dead raider.
			continue;

		    targets.add(target);
		}

		if (targets.size() == 0)
		    return false;

		SWEntityInterface target = ARandom.itemFrom(targets);
			
		actor.schedule(new ForceChoke(target, messageRenderer));

		
		return false;
	}

}
