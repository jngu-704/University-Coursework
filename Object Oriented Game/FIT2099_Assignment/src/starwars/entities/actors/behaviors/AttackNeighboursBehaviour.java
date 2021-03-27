package starwars.entities.actors.behaviors;

import java.util.ArrayList;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.ARandom;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.actions.Attack;

public class AttackNeighboursBehaviour extends BehaviourInterface {

    private boolean avoidFriendlies;
    private boolean avoidNonActors;
    private String message;
    private MessageRenderer messageRenderer;

    public AttackNeighboursBehaviour(SWActor attacker, SWWorld world, MessageRenderer m, boolean avoidFriendlies, boolean avoidNonActors, String message) {

	super(attacker, world);
	this.avoidFriendlies = avoidFriendlies;
	this.avoidNonActors = avoidNonActors;
	this.message = message;
	this.messageRenderer = m;
    }

    @Override
    public boolean ExecuteBehaviour() {

	ArrayList<SWEntityInterface> targets = new ArrayList<SWEntityInterface>();

	for (SWEntityInterface target : getLocalEntites()) {
	    if (target == actor) // Why are you hitting yourself?
		continue;

	    if (target.isDead()) // Don't beat a dead raider.
		continue;

	    // I keep going back and forth on this. If you give it the attack affordance it makes sense
	    // but then the player gets asked to attack the canteen he's holding.
//	    if (target.getAffordance(Attack.class) == null) // Not an attackable thing.
//		continue;

	    if (avoidNonActors && !(target instanceof SWActor)) // Not an actor.
		continue;

	    if (avoidFriendlies && (target instanceof SWActor) && ((SWActor) target).getTeam() == actor.getTeam()) // No friendly fire.
		continue;

	    targets.add(target);
	}

	if (targets.size() == 0)
	    return false;

	SWEntityInterface target = ARandom.itemFrom(targets);
	actor.say(String.format(message, actor.getShortDescription(), target.getShortDescription()));
	double x = Math.random();
	
	if (actor.getShortDescription() == "StormTrooper"){
		if(Math.random()> 0.75){
			actor.schedule(new Attack(target, messageRenderer));
			return true;
		}else{
			actor.say("Stormtrooper shoots wildly!");
			return true;
		}
	}
	
	
	// You only need an affordance on an item so the player has an option to attack it.
	actor.schedule(new Attack(target, messageRenderer));
	return true;
    }
}
