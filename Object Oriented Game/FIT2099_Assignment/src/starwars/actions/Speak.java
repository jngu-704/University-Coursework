package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;

/** Mostly just here to be a time waster so Ben doesn't wander off during training
 */
public class Speak extends SWAction {

    private String message;
    public Speak(MessageRenderer m, String message) {
	super(m);
	this.message = message;
    }

    @Override
    public boolean canDo(SWActor actor) {
	return false;
    }

    @Override
    public void act(SWActor actor) {
	actor.say(String.format("%s says, \"%s\"", actor.getShortDescription(), message));
    }

    @Override
    public String getDescription() {
	return null;
    }

}
