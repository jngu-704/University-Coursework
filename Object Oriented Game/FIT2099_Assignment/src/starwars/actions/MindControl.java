package starwars.actions;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.swinterfaces.SWGridController;

public class MindControl extends SWAffordance {

    protected SWWorld world;

    public MindControl(SWEntityInterface theTarget, SWWorld world, MessageRenderer m) {
	super(theTarget, m);
	this.world = world;
    }

    @Override
    public boolean canDo(SWActor actor) {
	return actor.hasCapability(Capability.MIND_CONTROLLER) && !getTarget().isDead();
    }

    @Override
    public void act(SWActor actor) {
	ArrayList<ActionInterface> cmds = new ArrayList<ActionInterface>();
	for (CompassBearing dir : CompassBearing.values()) {
	    if (world.canMove((SWActor)target, dir) )
		cmds.add(new Move(dir, messageRenderer, world));
	}

	messageRenderer.render(String.format("Force %s to move in which direction?", target.getShortDescription()));
	SWActionInterface move = SWGridController.getSelection(cmds);
	target.say(String.format("%s looks confused, but does it anyway.", target.getShortDescription()));
	((SWActor)target).schedule(move);
    }

    @Override
    public String getDescription() {
	return "mind control " + target.getShortDescription();
    }
}
