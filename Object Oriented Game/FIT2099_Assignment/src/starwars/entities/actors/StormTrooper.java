package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.TrainJedi;
import starwars.entities.Blaster;
import starwars.entities.actors.behaviors.AttackNeighboursBehaviour;
import starwars.entities.actors.behaviors.CallBackupBehaviour;
import starwars.entities.actors.behaviors.WanderBehaviour;

public class StormTrooper extends SWOrganicActor {

	/**
	 * Constructor for the <code>StormTrooper</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>StormTrooper</code></li>
	 * 	<li>Initialize the world for this <code>StormTrooper</code></li>
	 *  <li>Set the <code>Team</code> for this <code>StormTrooper</code></li>
	 * 	<li>Set the hit points for this <code>StormTrooper</code></li>
	 * 	It will give the StormTrooper various behaviours and capability and also give him a blaster
	 * </ul>
	 * 
	 * @param team The team for this actor
	 * @param hitpoints The hitpoints for this actor
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>StormTrooperr</code> belongs to
	 */
	public StormTrooper(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		
		super(Team.EVIL, hitpoints, m, world);
		this.setSymbol("S");
		behaviours.add(new AttackNeighboursBehaviour(this, world, m, true, false, "%s has attacked %2s"));	
		behaviours.add(new CallBackupBehaviour(this,world));
		behaviours.add(new WanderBehaviour(this, world));
		SWEntity weapon = new Blaster(m);
		setItemCarried(weapon);
		addCapability(Capability.WEAPON);
		addCapability(Capability.CALLBACKUP);

	}
	
	/**
	 * This method will execute the behaviours of the Stormtrooper
	 */
	
    @Override
    protected void executeBehaviours() {
 	
			super.executeBehaviours();
		
    }
	/**
	 * Returns the Stormtrooper string
	 */
    public String getShortDescription() {
	return "StormTrooper";
    }
	/**
	 * Returns the Stormtrooper string
	 */
    public String getLongDescription() {
	return "StormTrooper";
    }

}
