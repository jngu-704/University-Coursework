package starwars.actions;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.DroidParts;

public class DismantleDroid extends SWAffordance {

    public DismantleDroid(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
    }

    @Override
    public boolean canDo(SWActor actor) {
	return true;
    }

    @Override
    public void act(SWActor actor) {
	EntityManager<SWEntityInterface, SWLocation> entityManager = SWAction.getEntitymanager();
	entityManager.setLocation(new DroidParts(messageRenderer), entityManager.whereIs(getTarget()));
	entityManager.remove(getTarget());
	actor.say(String.format("%s has been reduced to parts.", target.getShortDescription()));
    }

    @Override
    public String getDescription() {
	return String.format("Dismantle %s into parts", target.getShortDescription());
    }
}
