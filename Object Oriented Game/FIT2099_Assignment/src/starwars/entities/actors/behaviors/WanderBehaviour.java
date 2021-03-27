package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import starwars.ARandom;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Move;

public class WanderBehaviour extends BehaviourInterface {

    public WanderBehaviour(SWActor actor, SWWorld world) {
	super(actor, world);
    }

    @Override
    public boolean ExecuteBehaviour() {
    if(this.actor.getShortDescription().contains("Tusken Raider")){
    	if (Math.random() > 0.5)
    		return false;
    }

	ArrayList<Direction> possibledirections = new ArrayList<Direction>();

	// build a list of available directions
	for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
	    if (SWWorld.getEntitymanager().seesExit(actor, d)) {
		possibledirections.add(d);
	    }
	}

	Direction heading = ARandom.itemFrom(possibledirections);
	Move myMove = new Move(heading, world.getMessageRenderer(), world);
	actor.schedule(myMove);

	return true;
    }
}
