package starwars.entities.actors.behaviors;

import edu.monash.fit2099.gridworld.Grid;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.actions.Move;

public class FollowBehaviour extends BehaviourInterface {

    private SWActor owner;
    //the Grid in which the owner was set
    private SWGrid current;

    public FollowBehaviour(SWActor actor, SWWorld world, SWActor owner) {
	super(actor, world);
	this.owner = owner;
	current = actor.world.getGrid();
    }

    public boolean hasOwner() {
	return owner != null;
    }

    public void setOwner(SWActor newOwner) {
	owner = newOwner;
    }

    @Override
    public boolean ExecuteBehaviour() {
	if (owner == null)
	    return false;

		
	//TODO go the full A* 
	SWLocation a = entityManager.whereIs(actor);
	SWLocation b = entityManager.whereIs(owner);
	
	//checks if the owner has left the grid
	if(current != owner.world.getGrid()){
		//if owner isnt in grid, the companion will be teleported to
		//the grid in which the owner is in
		SWLocation location;
		location = owner.world.getGrid().getLocationByCoordinates(0, 0);
		entityManager.setLocation(actor, location);
		current = owner.world.getGrid();
		return true;
	}
	

	
	if (a == b)
	    return true;

	for (Grid.CompassBearing heading : Grid.CompassBearing.values()) {
	    if (a.getNeighbour(heading) == b) {
		Move myMove = new Move(heading, world.getMessageRenderer(), world);
		actor.schedule(myMove);
		return true;
	    }
	}
	return false;
    }
}
