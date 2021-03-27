package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MindControl;
import starwars.actions.TakeOwnership;
import starwars.entities.actors.behaviors.FollowBehaviour;

public class PrincessLeiaOrgana extends SWOrganicActor implements  SWOwnerInterface{
//
	protected FollowBehaviour followBehaviour;
	
	public PrincessLeiaOrgana(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		// TODO Auto-generated constructor stub		
		this.setSymbol("L");
		this.setShortDescription("Leia");
		this.setLongDescription("Princess Leia Organa");
		this.removeAffordance(MindControl.class);
		addAffordance(new TakeOwnership(this, messageRenderer));
		
		
		followBehaviour = new FollowBehaviour(this, world, null);
		behaviours.add(followBehaviour);
	}

    @Override
    public boolean hasOwner() {
	return followBehaviour.hasOwner();
    }

    @Override
    public void setOwner(SWActor newOwner) {
	followBehaviour.setOwner(newOwner);
    }
    
    @Override
    public void act() {
    	if (isDead()){
    		this.world.setLosegame(true);
    		return;
    	}
		executeBehaviours();
    }
}
