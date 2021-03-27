package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.MonMothmaCheck;

public class MonMothma extends SWOrganicActor {
	
	public MonMothma(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.setSymbol("M");
		this.setShortDescription("Mon");
		this.setLongDescription("Mon Mothma");
		this.addAffordance(new MonMothmaCheck(this, m));
	}
	
}
