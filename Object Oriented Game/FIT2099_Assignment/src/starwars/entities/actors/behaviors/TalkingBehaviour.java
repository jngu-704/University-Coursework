package starwars.entities.actors.behaviors;

import java.util.*;

import starwars.ARandom;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.actions.Speak;

public class TalkingBehaviour extends BehaviourInterface {

    private ArrayList<String> quotes;
    
    public TalkingBehaviour(SWActor actor, SWWorld world, ArrayList<String> quotes) {
	super(actor, world);
	this.quotes = quotes;
    }

    @Override
    public boolean ExecuteBehaviour() {
	if (Math.random() < 0.9)
	    return false;

	actor.schedule(new Speak(messageRenderer, ARandom.itemFrom(quotes)));
	return true;
    }
}
