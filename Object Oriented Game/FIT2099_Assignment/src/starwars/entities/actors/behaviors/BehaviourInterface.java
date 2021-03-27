package starwars.entities.actors.behaviors;

import java.util.List;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Take;

public abstract class BehaviourInterface {

    protected SWActor actor;
    protected SWWorld world;
    protected MessageRenderer messageRenderer;
    protected EntityManager<SWEntityInterface, SWLocation> entityManager;

    public BehaviourInterface(SWActor actor, SWWorld world) {
	this.actor = actor;
	this.world = world;
	if (world != null) {
	    entityManager = world.getEntityManager();
	    messageRenderer = world.getMessageRenderer();
	}
    }

    public abstract boolean ExecuteBehaviour();

    /**
     * 
     * @return a list of entites in the same location as the actor.
     * 
     */
    protected List<SWEntityInterface> getLocalEntites() {
	//Should really be on EntityManager, but that's engine biz.
	return entityManager.contents(entityManager.whereIs(actor));
    }

    protected SWEntityInterface getLocalEntityWithAffordance(Class<? extends SWAffordance> type) {
	for (SWEntityInterface item : getLocalEntites()) {
	    SWAffordance affordance = item.getAffordance(type);
	    if (affordance != null && affordance.canDo(actor))
		return item;
	}
	return null;
    }

    protected SWEntityInterface getLocalEntityOfType(Class<? extends SWEntityInterface> type) {
	for (SWEntityInterface item : getLocalEntites()) {
	    if (item.getClass() == type)
		return item;
	}
	return null;
    }
    
    protected SWEntityInterface getLocalTakeableEntityOfType(Class<? extends SWEntityInterface> type) {
	for (SWEntityInterface item : getLocalEntites()) {
	    if (item.getClass() == type && item.getAffordance(Take.class)!=null)
		return item;
	}
	return null;
    }
    
    protected SWEntityInterface getLocalEntityWithCapability(Capability capability) {
	for (SWEntityInterface item : getLocalEntites()) {
	    if (item.hasCapability(capability))
		return item;
	}
	return null;
    }
}
