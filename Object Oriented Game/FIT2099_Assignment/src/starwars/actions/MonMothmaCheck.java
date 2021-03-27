package starwars.actions;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;

public class MonMothmaCheck extends SWAffordance {

	public MonMothmaCheck(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canDo(SWActor actor) {
	
		return actor.hasCapability(Capability.MONMOTHMACHECK);
	}

	@Override
	public void act(SWActor actor) {
		
		//check where actor is
		SWLocation a = actor.world.getEntityManager().whereIs(actor);
		int winGameCount = 0;
		List<SWEntityInterface> contents = actor.world.getEntityManager().contents(a);
		//check if the other actors are in the same location
		if (contents.size() > 1) { // if it is equal to one, the only thing here is this Player, so there is nothing to report

			for (SWEntityInterface entity : contents) {
				if (entity.getShortDescription()== "Luke" || entity.getShortDescription() == "Leia" || entity.getShortDescription() == "R2-D2") { // don't include self in scene description
					winGameCount += 1;
				}
			}
			//if the 3 required actors are there the game will be set to win
			if(winGameCount == 3){
				actor.world.setWingame(true);
			}
			else{
			//else it will output Mon Mothma's quote
				actor.say("What are you doing here, farmboy? bring us General Organa and the plans!");
				actor.schedule(new MonMothmaCheck(actor, messageRenderer));
			}
		}
		
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Talk to Mon Mothma";
	}

}
