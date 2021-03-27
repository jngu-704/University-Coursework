package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.AttackNeighboursBehaviour;
import starwars.entities.actors.behaviors.ForceChokeBehaviour;
import starwars.entities.actors.behaviors.WanderBehaviour;

public class DarthVader extends SWOrganicActor {
	/**
	 * Constructor for the <code>Darthvader</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for<code>DarthVader</code></li>
	 * 	<li>Initialize the world for <code>DarthVader</code></li>
	 *  <li>Set the <code>Team</code> for<code>DarthVader</code></li>
	 * 	<li>Set the hit points for<code>DarthVader</code></li>
	 * 	It will give Darthvader various behaviours and capability and also give him a blaster
	 * </ul>
	 * 
	 * @param team The team for this actor
	 * @param hitpoints The hitpoints for this actor
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which<code>DarthVader</code> belongs to
	 */
	public DarthVader(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		addCapability(Capability.FORCECHOKER);
		this.setSymbol("D");
		this.setShortDescription("Darth Vader");
		
		behaviours.add(new ForceChokeBehaviour(this,world));
		behaviours.add(new WanderBehaviour(this, world));

		// TODO Auto-generated constructor stub
	}
	/**
	 * This method will execute the behaviours of DarthVader
	 */
	@Override
    protected void executeBehaviours() {
	//say(describeLocation()); // Too verbose, good for debugging though.
	super.executeBehaviours();
    }
	/**
	 * Returns the DarthVader string
	 */
    public String getShortDescription() {
	return "Darth Vader";
    }
	/**
	 * Returns the DarthVader string
	 */
    public String getLongDescription() {
	return "Darth Vader";
    }
    /**
     * Checks if DarthVader is dead to check for winning/losing
     */
    @Override
    public void act() {
    	if (isDead()){
    		this.world.setWingame(true);
    		return;
    	}
		executeBehaviours();
    }
}
