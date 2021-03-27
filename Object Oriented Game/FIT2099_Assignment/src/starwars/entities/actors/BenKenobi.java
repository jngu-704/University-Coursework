package starwars.entities.actors;

import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.*;
import starwars.entities.LightSaber;
import starwars.entities.actors.behaviors.*;

/**
 * Ben (aka Obe-Wan) Kenobi.
 * 
 * At this stage, he's an extremely strong critter with a
 * <code>Lightsaber</code>
 * who wanders around in a fixed pattern and neatly slices any Actor not on his
 * team with his lightsaber.
 * 
 * Note that you can only create ONE Ben.
 * 
 * @author rober_000
 *
 */
public class BenKenobi extends SWOrganicActor {

    private static BenKenobi ben = null; // yes, it is OK to return the static instance!

    private BenKenobi(MessageRenderer m, SWWorld world, Direction[] moves) {
	super(Team.GOOD, 1000, m, world);
	this.setSymbol("B");
	this.takeDamage(50);

	this.setShortDescription("Ben Kenobi");
	this.setLongDescription("Ben Kenobi, an old man who has perhaps seen too much");
	SWEntity weapon = new LightSaber(m);
	weapon.removeAffordance(Take.class);
	weapon.addAffordance(new Leave(weapon, m));
	setItemCarried(weapon);
	addCapability(Capability.WIELDS_LIGHTSABER);

	this.addAffordance(new TrainJedi(this, m));
	this.removeAffordance(MindControl.class);
	behaviours.add(new TrainerBehaviour(this, world));
	behaviours.add(new AttackNeighboursBehaviour(this, world, m, true, true, "%s suddenly looks sprightly and attacks %2s"));
	behaviours.add(new HealBehaviour(this, world, Capability.WATER_BASED));
	behaviours.add(new PatrolBehaviour(this, world, moves));
    }

    // This effectively makes BenKenobi a global variable.
    public static BenKenobi getBenKenobi(MessageRenderer m, SWWorld world, Direction[] moves) {

	if (ben == null) {
	    ben = new BenKenobi(m, world, moves);
	}
	return ben;
    }
}
