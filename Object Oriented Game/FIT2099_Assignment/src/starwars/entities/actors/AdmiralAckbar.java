package starwars.entities.actors;

import java.util.ArrayList;
import java.util.Arrays;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.TalkingBehaviour;


public class AdmiralAckbar extends SWOrganicActor {

	public AdmiralAckbar(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
		super(team, hitpoints, m, world);
		// TODO Auto-generated constructor stub
		this.setSymbol("A");
		this.setShortDescription("Ackbar");
		this.setLongDescription("Admiral Ackbar");
		ArrayList<String> quote = new ArrayList<>(Arrays.asList("It’s a trap!"));
		behaviours.add(new TalkingBehaviour(this, world, quote));
	}

	
}
