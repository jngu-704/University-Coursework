/**
 * Class that represents an Actor (i.e. something that can perform actions) in the starwars world.
 * 
 * @author ram
 * 
 * @modified 20130414 dsquire
 * 	- changed constructor so that affordances that all SWActors must have can be added
 * 	- changed team to be an enum rather than a string
 */
/*
 * Change log
 * 2017-01-20: Added missing Javadocs and improved comments (asel)
 * 2017-02-08: Removed the removeEventsMethod as it's no longer required.
 * 			   Removed the tick and act methods for SWActor as they are never called
 */
package starwars;

import java.util.ArrayList;
import java.util.HashSet;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.ActionInterface;
import edu.monash.fit2099.simulator.matter.Actor;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.actions.*;
import starwars.entities.actors.behaviors.BehaviourInterface;

public abstract class SWActor extends Actor<SWActionInterface> implements SWEntityInterface {

    /** the <code>Team</code> to which this <code>SWActor</code> belongs to **/
    private Team team;

    /** The world this <code>SWActor</code> belongs to. */
    public SWWorld world;

    /** Scheduler to schedule this <code>SWActor</code>'s eventss */
    protected static Scheduler scheduler;

    /**
     * The item carried by this <code>SWActor</code>. <code>itemCarried</code>
     * is null if this <code>SWActor</code> is not carrying an item
     */
    private SWEntity itemCarried;

    protected ArrayList<BehaviourInterface> behaviours = new ArrayList<BehaviourInterface>();

    // Can't inherit from an Actor and an Entity, so wrap SWEntity.
    private SWEntity innerEntity;

    /**
     * Constructor for the <code>SWActor</code>.
     * <p>
     * The constructor initializes the <code>actions</code> list of this
     * <code>SWActor</code>.
     * <p>
     * By default,
     * <ul>
     * <li>All <code>SWActor</code>s can be attacked.</li>
     * <li>Have their symbol set to '@'</li>
     * </ul>
     * 
     * @param team to which this <code>SWActor</code> belongs to
     * @param hitpoints initial hitpoints of this <code>SWActor</code> to start with
     * @param m message renderer for this <code>SWActor</code> to display messages
     * @param world the <code>World</code> to which <code>SWActor</code> belongs
     * 
     * @see #team
     * @see #hitpoints
     * @see #world
     * @see starwars.actions.Attack
     */
    public SWActor(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
	super(m);
	actions = new HashSet<SWActionInterface>();
	this.team = team;
	this.world = world;
	innerEntity = new SWEntity(m);
	innerEntity.setSymbol("@");
	innerEntity.setMaxAndCurrentHitPoints(hitpoints);

	//SWActors are given the Attack affordance hence they can be attacked
	this.addAffordance(new Attack(this, m));
    }

    /**
     * Sets the <code>scheduler</code> of this <code>SWActor</code> to a new
     * <code>Scheduler s</code>
     * 
     * @param s the new <code>Scheduler</code> of this <code>SWActor</code>
     * @see #scheduler
     */
    public static void setScheduler(Scheduler s) {
	scheduler = s;
    }

    /**
     * Returns the team to which this <code>SWActor</code> belongs.
     * <p>
     * Useful in comparing the teams different <code>SWActor</code> belong to.
     * 
     * @return the team of this <code>SWActor</code>
     * @see #team
     */
    public Team getTeam() {
	return team;
    }

    /**
     * Returns an ArrayList containing this Actor's available Actions, including
     * the Affordances of items that the Actor is holding.
     * 
     * @author ram
     */
    public ArrayList<SWActionInterface> getActions() {
	ArrayList<SWActionInterface> actionList = super.getActions();

	//If the actor is carrying anything, look for its affordances and add them to the list
	SWEntityInterface item = getItemCarried();
	if (item != null)
	    for (Affordance aff : item.getAffordances())
		if (aff instanceof SWAffordance)
		    actionList.add((SWAffordance) aff);
	return actionList;
    }

    /**
     * Returns the item carried by this <code>SWActor</code>.
     * <p>
     * This method only returns the reference of the item carried
     * and does not remove the item held from this <code>SWActor</code>.
     * <p>
     * If this <code>SWActor</code> is not carrying an item this method will
     * return null.
     * 
     * @return the item carried by this <code>SWActor</code> or null if no item is held by this <code>SWActor</code>
     * @see #itemCarried
     */
    public SWEntity getItemCarried() {
	return itemCarried;
    }

    /**
     * Sets the team of this <code>SWActor</code> to a new team
     * <code>team</code>.
     * <p>
     * Useful when the <code>SWActor</code>'s team needs to change dynamically during the simulation.
     * For example, a bite from an evil actor makes a good actor bad.
     *
     * @param team the new team of this <code>SWActor</code>
     * @see #team
     */
    public void setTeam(Team team) {
	this.team = team;
    }

    /**
     * Assigns this <code>SWActor</code>'s <code>itemCarried</code> to a new item <code>target</code>
     * <p>
     * This method will replace items already held by the <code>SWActor</code> with the <code>target</code>.
     * A null <code>target</code> would signify that this <code>SWActor</code> is not carrying an item anymore.
     * 
     * @param target the new item to be set as item carried
     * @see #itemCarried
     */
    public void setItemCarried(SWEntity target) {
	this.itemCarried = target;
    }

    /**
     * This method will poll this <code>SWActor</code>'s current <code>Location loc</code>
     * to find potential exits, and replaces all the instances of <code>Move</code>
     * in this <code>SWActor</code>'s command set with <code>Moves</code> to the new exits.
     * <p>
     * This method doesn't affect other non-movement actions in this
     * <code>SWActor</code>'s command set.
     * 
     * @author ram
     * @param loc this <code>SWActor</code>'s location
     * @pre <code>loc</code> is the actual location of this <code>SWActor</code>
     */
    public void resetMoveCommands(Location loc) {
	HashSet<SWActionInterface> newActions = new HashSet<SWActionInterface>();

	// Copy all the existing non-movement options to newActions
	for (SWActionInterface a : actions) {
	    if (!a.isMoveCommand())
		newActions.add(a);
	}

	// add new movement possibilities
	for (CompassBearing d : CompassBearing.values()) {
	    if (loc.hasExit(d)) //if there is an exit from the current location in direction d, add that as a Move command
		newActions.add(new Move(d, messageRenderer, world));
	}

	// replace command list of this SWActor
	this.actions = newActions;

	// TODO: This assumes that the only actions are the Move actions. This will clobber any others. Needs to be fixed.
	/*
	 * Actually, that's not the case: all non-movement actions are
	 * transferred to newActions before the movements are transferred. --ram
	 */
    }

    public void schedule(ActionInterface action) {
	scheduler.schedule(action, this, action.getDuration());
    }

    @Override
    public void act() {
    	if (isDead())
    		return;

		executeBehaviours();
    }

    protected void executeBehaviours() {
    	for (BehaviourInterface behaviour : behaviours) {
    		if (behaviour.ExecuteBehaviour())
	
    			return;
		}
    }
    
    public BehaviourInterface getBehaviour(Class<? extends BehaviourInterface> type)
    {
	for (BehaviourInterface b : behaviours) {
	    if (b.getClass() == type){
		return (BehaviourInterface)b;
	    }
	}
	return null;
    }
    
    public void removeBehaviour(Class<? extends BehaviourInterface> type) {
	behaviours.remove(getBehaviour(type));
    }

    // Entity wrapper methods.
    // SWActor is an Actor (subclass of Entity), so it can't be a SWEntity because of single inheritance.
    // I need more affordance methods, which are on SWEntity, so I'm wrapping a SWEntity and delegating all affordance behaviour to it.
    // It turned out to be only marginally less awkward than copy pasting all the SWEntityInteface code out of SWEntity. But, DRY.

    @Override
    public void addAffordance(Affordance a) {
	innerEntity.addAffordance(a);
    }

    @Override
    public void removeAffordance(Affordance a) {
	innerEntity.removeAffordance(a);
    }

    @Override
    public Affordance[] getAffordances() {
	return innerEntity.getAffordances();
    }

    @Override
    public SWAffordance getAffordance(Class<?> type) {
	return innerEntity.getAffordance(type);
    }

    @Override
    public void removeAffordance(Class<?> type) {
	innerEntity.removeAffordance(type);
    }

    @Override
    public String getSymbol() {
	return innerEntity.getSymbol();
    }

    @Override
    public void setSymbol(String s) {
	innerEntity.setSymbol(s);
    }

    @Override
    public boolean hasCapability(Capability c) {
	return innerEntity.hasCapability(c);
    }

    @Override
    public void addCapability(Capability c) {
	innerEntity.addCapability(c);
    }

    @Override
    public void removeCapability(Capability c) {
	innerEntity.removeCapability(c);
    }

    /**
     * Method insists damage on this <code>SWActor</code> by reducing a
     * certain amount of <code>damage</code> from this <code>SWActor</code>'s
     * <code>hitpoints</code>
     * 
     * @param damage the amount of <code>hitpoints</code> to be reduced
     * @pre <code>damage</code> should not be negative
     */
    @Override
    public void takeDamage(int hitPoints) {
	innerEntity.takeDamage(hitPoints);
    }

    @Override
    public void heal(int damage) {
	innerEntity.heal(damage);
    }

    /**
     * Returns the hit points of this <code>SWActor</code>.
     * 
     * @return the hit points of this <code>SWActor</code>
     * @see #hitpoints
     * @see #isDead()
     */
    @Override
    public int getHitPoints() {
	return innerEntity.getHitPoints();
    }

    @Override
    public int getMaxHitPoints() {
	return innerEntity.getMaxHitPoints();
    }
    
    @Override
    public boolean isDead() {
	return innerEntity.isDead();
    }
    
    @Override
    public void movedToLocation(SWLocation loc) { }
}
