package starwars.entities.actors;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.Team;

public class VanillaActor extends SWOrganicActor {

    public VanillaActor(Team team, int hitpoints, String name, String symbol, MessageRenderer m, SWWorld world) {
	super(team, hitpoints, m, world);
	this.setSymbol(symbol);
	this.setShortDescription(name);
	this.setLongDescription(name);
    }
}
