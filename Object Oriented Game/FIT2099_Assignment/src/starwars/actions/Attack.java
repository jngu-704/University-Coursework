package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.SWEntityInterface;

/**
 * Command to attack entities.
 * 
 * This affordance is attached to all attackable entities
 * 
 * @author David.Squire@monash.edu (dsquire)
 */
/*
 * Change log
 * 2017/02/03 Fixed the bug where the an actor could attack another actor in the same team (asel)
 * 2017/02/08 Attack given a priority of 1 in constructor (asel)
 */
public class Attack extends SWAffordance implements SWActionInterface {

    /**
     * Constructor for the <code>Attack</code> class. Will initialize the
     * <code>messageRenderer</code> and
     * give <code>Attack</code> a priority of 1 (lowest priority is 0).
     * 
     * @param theTarget the target being attacked
     * @param m message renderer to display messages
     */
    public Attack(SWEntityInterface theTarget, MessageRenderer m) {
	super(theTarget, m);
	priority = 1;
    }

    /**
     * A String describing what this <code>Attack</code> action will do, suitable for display on a user interface.
     * 
     * @return String comprising "attack " and the short description of the target of this <code>Affordance</code>
     */
    @Override
    public String getDescription() {
	return "attack " + this.target.getShortDescription();
    }

    /**
     * Determine whether a particular <code>SWActor a</code> can attack the target.
     * 
     * @author dsquire
     * @param a the <code>SWActor</code> being queried
     * @return true any <code>SWActor</code> can always try an attack, it just won't do much good unless this <code>SWActor a</code> has a suitable weapon.
     */
    @Override
    public boolean canDo(SWActor actor) {
	return !(getTarget().isDead() || getTarget() == actor.getItemCarried());
    }

    /**
     * Perform the <code>Attack</code> command on an entity.
     * <p>
     * This method does not perform any damage (an attack) if,
     * <ul>
     * <li>The target of the <code>Attack</code> and the <code>SWActor a</code>
     * are in the same <code>Team</code></li>
     * <li>The <code>SWActor a</code> is holding an item without the
     * <code>WEAPON Capability</code></li>
     * </ul>
     * <p>
     * else it would damage the entity attacked, tires the attacker, and blunts
     * any weapon used for the attack.
     * 
     * @author dsquire - adapted from the equivalent class in the old Eiffel version
     * @author Asel - bug fixes.
     * @param a the <code>SWActor</code> who is attacking
     * @pre this method should only be called if the <code>SWActor a</code> is alive
     * @pre an <code>Attack</code> must not be performed on a dead <code>SWActor</code>
     * @post if a <code>SWActor</code>dies in an <code>Attack</code> their <code>Attack</code> affordance would be removed
     * @see starwars.SWActor#isDead()
     * @see starwars.Team
     */
    @Override
    public void act(SWActor attacker) {
	int energyForAttackWithWeapon = 1;//the amount of energy required to attack with a weapon
	SWEntityInterface target = getTarget();

	attacker.say(attacker.getShortDescription() + " is attacking " + target.getShortDescription() + "!");

	if (attackerUsesWeapon(attacker)) {
	    SWEntity itemCarried = attacker.getItemCarried();
	    target.takeDamage(itemCarried.getHitPoints() + 1); // blunt weapon won't do much, but it will still do some damage
	    attacker.takeDamage(energyForAttackWithWeapon); // actor uses energy to attack
	    itemCarried.takeDamage(1); // weapon gets blunt
	} else {
	    target.takeDamage((attacker.getHitPoints() / 20) + 1); // a bare-handed attack doesn't do much damage.
	    attacker.takeDamage(2 * energyForAttackWithWeapon); // actor uses energy. It's twice as tiring as using a weapon
	}

	//After the attack
	if (attacker.isDead()) { //the actor who attacked is dead after the attack
	    attacker.setLongDescription(attacker.getLongDescription() + ", that died of exhaustion while attacking someone");
	    attacker.removeAffordance(Attack.class); //remove the attack affordance of the dead actor so it can no longer be attacked
	}

	if (target.isDead()) {
	    target.removeAffordance(Attack.class); //remove the attack affordance of the dead actor so it can no longer be attacked
	    if (target instanceof SWActor)
		target.setLongDescription(target.getLongDescription() + ", that was killed in a fight");
	    else
		target.setLongDescription(target.getLongDescription() + ", that was destroyed in a fight");
	}
    }

    private boolean attackerUsesWeapon(SWActor attacker) {
	SWEntity itemCarried = attacker.getItemCarried();
	return itemCarried != null && itemCarried.hasCapability(Capability.WEAPON) && itemCarried.canBeUsedBy(attacker) && !itemCarried.isDead();
    }
}
