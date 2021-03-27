package starwars;

import java.util.HashSet;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.Entity;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;

/**
 * Class that represents inanimate objects in the Star Wars world. Objects that
 * cannot move for example trees.
 * 
 * @author ram
 * @see edu.monash.fit2099.simulator.matter.Entity
 * @see SWEntityInterface
 */

public class SWEntity extends Entity implements SWEntityInterface {

    /**
     * A string symbol that represents this <code>SWEntity</code>, suitable for
     * display
     */
    private String symbol;

    /** A set of <code>Capabilities</code> of this <code>SWEntity</code> */
    private HashSet<Capability> capabilities = new HashSet<Capability>();

    /** The amount of <code>hitpoints</code> of this <code>SWEntity</code>. */
    protected int currentHitPoints = 0; // Not all non-actor entities will make use of this

    protected int maxHitPoints = 0;

    /**
     * Constructor for this <code>SWEntity</code>. Will initialize this
     * <code>SWEntity</code>'s
     * <code>messageRenderer</code> and set of capabilities.
     * 
     * @param m the <code>messageRenderer</code> to display messages
     */
    protected SWEntity(MessageRenderer m) {
	super(m);
    }

    /**
     * Returns a String symbol representing this <code>SWEntity</code>.
     * 
     * @return symbol a String that represents this <code>SWEntity</code>
     * @see #symbol
     */
    @Override
    public String getSymbol() {
	return symbol;
    }

    /**
     * Sets the symbol of this <code>SWEntity</code> with a new string
     * <code>s</code>.
     * 
     * @param s
     *            the new string symbol for this <code>SWEntity</code>
     * @see #symbol
     */
    @Override
    public void setSymbol(String s) {
	symbol = s;
    }

    @Override
    public boolean hasCapability(Capability c) {
	return capabilities.contains(c);
    }

    @Override
    public void addCapability(Capability c) {
	capabilities.add(c);
    }

    @Override
    public void removeCapability(Capability c) {
	capabilities.remove(c);
    }
    
    @Override
    public boolean isDead() {
	return currentHitPoints <= 0;
    }
    
    @Override
    public int getHitPoints() {
	return currentHitPoints;
    }

    @Override
    public int getMaxHitPoints() {
	return maxHitPoints;
    }

    /**
     * Sets the <code>hitpoints</code> of this <code>SWEntity</code>
     * to a new number of hit points <code>p</code>.
     * 
     * @param p the new number of <code>hitpoints</code>
     */
    public void setHitPoints(int hitPoints) {
	currentHitPoints = hitPoints;
    }
    
    public void setMaxAndCurrentHitPoints(int hitPoints) {
	maxHitPoints = hitPoints;
	currentHitPoints = hitPoints;
    }

    @Override
    public void takeDamage(int hitPoints) {
	//Precondition 1: Ensure that the damage is not negative
	assert (hitPoints >= 0) : "damage on SWEntity must not be negative";
	this.currentHitPoints = Math.max(currentHitPoints - hitPoints, 0);
    }

    @Override
    public void heal(int hitPoints) {
	//Precondition 1: Ensure that the damage is not negative
	assert (hitPoints >= 0) : "healing on SWEntity must not be negative";
	this.currentHitPoints = Math.min(currentHitPoints + hitPoints, maxHitPoints);
    }

    public boolean canBeUsedBy(SWActor user) {
	return true;
    }

    @Override
    public SWAffordance getAffordance(Class<?> type) {
	for (Affordance a : getAffordances()) {
	    if (a.getClass() == type)
		return (SWAffordance) a;
	}
	return null;
    }
    
    @Override
    public void removeAffordance(Class<?> type) {
	removeAffordance(getAffordance(type));
    }

    @Override
    public void movedToLocation(SWLocation loc) { }
}
