package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.InterstellarTravelToDeathStar;
import starwars.actions.InterstellarTravelToTatooine;
import starwars.actions.InterstellarTravelToYavin;
import starwars.actions.Take;

public class MillenniumFalcon extends SWEntity {

	public MillenniumFalcon(MessageRenderer m) {
		super(m);
		// TODO Auto-generated constructor stub
		this.shortDescription = "Millennium Falcon";
		this.longDescription = "Millennium Falcon a Corellian YT-1300f light freighter";
		this.setSymbol("F");
		this.setHitPoints(10000);
		this.addAffordance(new InterstellarTravelToTatooine(this, m));
		this.addAffordance(new InterstellarTravelToDeathStar(this, m));
		this.addAffordance(new InterstellarTravelToYavin(this, m));
		
	}
	
	

}
