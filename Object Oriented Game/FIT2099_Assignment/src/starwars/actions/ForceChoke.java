package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.Team;
/**
 * <code>SWAffordance</code> that lets <code>SWActor</code>s ForceChoke other entities.
 * 
 */
public class ForceChoke extends SWAffordance {
		

    /**
     * Constructor for the <code>ForceChoke</code> class. Will initialize the
     * <code>messageRenderer</code> 
     * 
     * @param theTarget the target being Force Choked
     * @param m message renderer to display messages
     */
	
	public ForceChoke(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}
	
    /**
     * Determine whether a particular <code>SWActor a</code> can ForceChoke.
     *
     * @param a the <code>SWActor</code> being queried
     * @return true any <code>SWActor</code> that contains the FORCECHOKER capability.
     */
	
	@Override
	public boolean canDo(SWActor actor) {

		return actor.hasCapability(Capability.FORCECHOKER);
	}
    /**
     * Perform the <code>ForceChoke</code> action.
     * <p>
	 * Checks if Luke is in the current position and randomizes numbers to lose the game for the player or ForceChoke them
     * <p>
     * This method will only be called if Darth Vader is on the location of another actor
     * 
     * @param 	a the <code>SWActor</code> who is ForceChoking
     */
	@Override
	public void act(SWActor actor) {
		if (getTarget().getShortDescription() == "Luke"){
			if(Math.random()> 0.5){
				
				if(getTarget().hasCapability(Capability.MIND_CONTROLLER)){
					if(Math.random() > 0.75){
						((SWActor) getTarget()).setTeam(Team.EVIL);
						actor.world.setLosegame(true);
						}
				}
				

			}else{
			getTarget().takeDamage(50);
			actor.say(String.format("%s is force choking", getTarget().getShortDescription())); 

			}
			
		
		}else{ 
			if(Math.random() > 0.5){
				getTarget().takeDamage(50);
				actor.say(String.format("%s is force choking", getTarget().getShortDescription())); 
			}
		}
	}
		

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
