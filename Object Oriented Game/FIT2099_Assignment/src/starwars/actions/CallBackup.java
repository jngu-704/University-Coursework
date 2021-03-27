package starwars.actions;

import java.util.List;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWLocation;
/**
 * <code>SWAction</code> that lets <code>SWActor</code>s CallBackup at their location.
 * 
 */
public class CallBackup extends SWAction {

	
    /**
     * Constructor for <code>CallBackup</code> class. Will initialize the callBackup and the world for the <code>CallBack</code>.
     * 
     * @param m <code>MessageRenderer</code> to display messages
     */
	
	public CallBackup(MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
    /**
     * Determine whether a particular <code>SWActor a</code> can CallBackup.
     *
     * @param a the <code>SWActor</code> being queried
     * @return true any <code>SWActor</code> that contains the CALLBACKUP capability.
     */
	@Override
	public boolean canDo(SWActor actor) {
		// TODO Auto-generated method stub
		return actor.hasCapability(Capability.CALLBACKUP);
	}
	
    /**
     * Perform the <code>CallBackup</code> action.
     * <p>
	 * Retrieves the current position of the actor and passes those values with the MessageRenderer to the createStormTrooper method in SWWorld
     * <p>
     * This method will only be called if the <code>StormTrooper a</code> is in a location by itself and Math.random() < 0.05
     * 
     * @param 	a the <code>SWActor</code> who is Calling Backup
     */
	@Override
	public void act(SWActor actor) {
		int x;
		int y;
		SWLocation location = actor.world.getEntityManager().whereIs(actor);

		x = (int)location.getLongDescription().charAt(11) -48;
		y = (int)location.getLongDescription().charAt(14) -48;
		
		actor.world.createStormTrooper(actor.world.getMessageRenderer(),x,y);

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
