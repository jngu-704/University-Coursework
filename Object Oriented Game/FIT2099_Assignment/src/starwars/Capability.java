package starwars;
/**
 * Capabilities that various entities may have.  This is useful in <code>canDo()</code> methods of 
 * <code>SWActionInterface</code> implementations.
 *  
 * @author 	ram
 * @see 	SWActionInterface
 * @see     starwars.entities.Fillable
 */

public enum Capability {
    	NONE,
	WEAPON,			//WEAPON capability allows an entity to Attack another entity which has the Attack Affordance
	FILLABLE,		//FILLABLE capability allows an entity to be refilled by another entity that has the Dip affordance.  Any FILLABLE Entity MUST implement the Fillable interface
	WATER_BASED,		//WATER_BASED capability allows an entity to be consumed by another entity
	OIL_BASED,		//OIL_BASED capability allows an entity to be consumed by another entity
	WIELDS_LIGHTSABER,
	MIND_CONTROLLER,
	JEDI_STUDENT,
	DROID_MECHANIC,
	DROID_REPAIR,
	FORCECHOKER,
	CALLBACKUP,
	PILOT,
	FLYTOTATOOINE,
	FLYTODEATHSTAR,
	FLYTOYAVIN,
	MONMOTHMACHECK,

}
