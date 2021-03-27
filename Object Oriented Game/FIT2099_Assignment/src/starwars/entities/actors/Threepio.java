package starwars.entities.actors;

import java.util.ArrayList;
import java.util.Arrays;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWWorld;
import starwars.Team;
import starwars.entities.actors.behaviors.TalkingBehaviour;

public class Threepio extends SWDroidActor {

    public Threepio(int hitpoints, MessageRenderer m, SWWorld world) {
	super(Team.GOOD, hitpoints, m, world);

	this.setShortDescription("C-3PO");
	this.setLongDescription("C-3PO, Protocol Droid");
	this.setSymbol("C");

	ArrayList<String> quotes = new ArrayList<>(Arrays.asList("Artoo says that the chances of survival are 725 to 1",
		"Surrender is a perfectly acceptable alternative in extreme circumstances",
		"Excuse me sir, but might I inquire as to what's going on?", " Sir, If I may venture an opinion... ",
		"Well, now, something's not right, because now I can't see!", "I'm terribly sorry about all this. After all, he's only a Wookiee.",
		"In his belly you will find a new definition of pain and suffering as you are slowly digested over a thousand years.",
		"I think Artoo has an amputee inside", " Wonderful. We are now a part of the tribe.", "At last, Master Luke's come to rescue me!",
		"If I told you half the things I've heard about this Jabba the Hutt, you'd probably short circuit.",
		"It is indeed true that at times like this R2 and I wish we were more than mechanical beings and we were really alive so we could share your feelings with you."));

	behaviours.add(new TalkingBehaviour(this, world, quotes));
	behaviours.add(followBehaviour);
    }
}
