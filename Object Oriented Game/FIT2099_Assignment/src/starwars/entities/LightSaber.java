package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;

/**
 * An extremely powerful, unbreakable weapon that implements the Weapon capability
 * 
 * It does not take damage with use.
 * 
 * This class does not implement all of the abilities shown in the films; there is
 * no capacity to deflect Blaster bolts, or parry other lightsabers;
 * 
 *  @author Robert
 *  @see {@link starwars.actions.Attack}
 */

public class LightSaber extends Weapon {

    /**
     * Constructor for the <code>LightSaber</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>LightSaber</code></li>
     * 	<li>Set the short description of this <code>LightSaber</code>>
     * 	<li>Set the long description of this <code>LightSaber</code> 
     * 	<li>Add a <code>Take</code> affordance to this <code>LightSaber</code> so it can be taken</li> 
     * </ul>
     * 
     * @param m <code>MessageRenderer</code> to display messages.
     * 
     * @see {@link starwars.actions.Take}
     * @see {@link starwars.Capability}
     * @see {@link starwars.actions.Chop} 1
     */
    public LightSaber(MessageRenderer m) {
	super(m, 100);

	this.shortDescription = "a Lightsaber";
	this.longDescription = "A lightsaber.  Bzzz-whoosh!";
    }

    /**
     * Lightsabers are unbreakable, so doing damage to them has no effect
     * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
     */
    @Override
    public void takeDamage(int damage) {
    }

    /**
     * A symbol that is used to represent the LightSaber on a text based user interface
     * 
     * @return 	A String containing a single character.
     * @see 	{@link starwars.SWEntityInterface#getSymbol()}
     */
    @Override
    public String getSymbol() {
	return "|";
    }

    public boolean canBeUsedBy(SWActor user) {
	return user.hasCapability(Capability.WIELDS_LIGHTSABER);
    }
}
