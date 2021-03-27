package starwars.entities.actors.behaviors;

import java.util.ArrayList;
import java.util.Arrays;

import edu.monash.fit2099.simulator.space.Direction;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Move;

public class PatrolBehaviour extends BehaviourInterface {

    private ArrayList<Direction> moves;
    private int position = 0;
    
    public PatrolBehaviour(SWActor actor, SWWorld world, Direction[] moves) {

	super(actor, world);
	this.moves = new ArrayList<Direction>(Arrays.asList(moves));
    }

    @Override
    public boolean ExecuteBehaviour() {

	Direction newdirection = getNextDirection();
	actor.say(actor.getShortDescription() + " moves " + newdirection);
	Move move = new Move(newdirection, messageRenderer, world);

	actor.schedule(move);
	return true;
    }

    public Direction getNextDirection() {

	Direction nextMove = moves.get(position);
	position = ++position % moves.size();
	return nextMove;
    }
}
