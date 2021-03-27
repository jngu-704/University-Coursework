package starwars;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid.CompassBearing;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import edu.monash.fit2099.simulator.space.LocationContainer;
import edu.monash.fit2099.simulator.space.World;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.entities.*;
import starwars.entities.actors.*;
import starwars.swinterfaces.SWGridController;
import starwars.swinterfaces.SWGridTextInterface;

/**
 * Class representing a world in the Star Wars universe.
 * 
 * @author ram
 */
/*
 * Change log
 * 2017-02-02: Render method was removed from Middle Earth
 * Displaying the Grid is now handled by the TextInterface rather
 * than by the Grid or MiddleWorld classes (asel)
 */
public class SWWorld extends World {
//
    /**
     * <code>SWGrid</code> of all the maps in this <code>SWWorld</code>
     */
    private SWGrid tatooineGrid;
    private SWGrid deathStarGrid;
    private SWGrid yavinGrid;
    /**Booleans to check the current gamestate*/
    private boolean wingame = false;
    private boolean losegame = false;
    
    
    /**
     * Array list to hold all the grids
     */
    
    private MessageRenderer messageRenderer;
  
	protected LocationContainer deathStarSpace; // space will contain instances of a Location subtype specified in client code
	protected LocationContainer yavinSpace; // space will contain instances of a Location subtype specified in client code
	
    /**
     * SWGrid variable to hold the current grid that the player is on
     */
	private SWGrid currentGrid;
	
	
    /**
     * The entity manager of the world which keeps track of
     * <code>SWEntities</code> and their <code>SWLocation</code>s
     */
    private static final EntityManager<SWEntityInterface, SWLocation> entityManager = new EntityManager<SWEntityInterface, SWLocation>();

    /**
     * Constructor of <code>SWWorld</code>. This will initialize the
     * <code>SWLocationMaker</code>
     * and the grids for Tatooine, Deathstar and YavinIV.
     */
    public SWWorld() {
	SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		
	//creating the grids for the maps
	tatooineGrid = new SWGrid(factory,10,10);
	deathStarGrid = new SWGrid(factory,10,10);
	yavinGrid = new SWGrid(factory,2,2);
	
	space = tatooineGrid;
	deathStarSpace = deathStarGrid;
	yavinSpace = yavinGrid;
	

	currentGrid = tatooineGrid;

    }
    
    /**
     * Returns the height of the <code>Grid</code>. Useful to the Views when
     * rendering the map.
     * 
     * @author ram
     * @return the height of the grid
     */
    
    
    public int height(String name) {
    	
    	if(name == "Tatooine"){
    		return space.getHeight();
    	}else if(name == "DeathStar"){
    		return deathStarSpace.getHeight();
    	}else if(name == "Yavin"){
    		return yavinSpace.getHeight();
    	}
    	return 0;
    }

    /**
     * Returns the width of the <code>Grid</code>. Useful to the Views when
     * rendering the map.
     * 
     * @author ram
     * @return the height of the grid
     */
    public int width(String name) {
    	if(name == "Tatooine"){
    		return space.getWidth();
    	}else if(name == "DeathStar"){
    		return deathStarSpace.getWidth();
    	}else if(name == "Yavin"){
    		return yavinSpace.getWidth();
    	}
    	return 0;

    }

    public MessageRenderer getMessageRenderer() {
	return messageRenderer;
    }

    /**
     * Sets up the Death Star map
     * 
     * @param iface a MessageRenderer to be passed onto newly-created entities
     */
    
    public void initializeDeathStar(MessageRenderer iface){
    	messageRenderer = iface;
    	SWLocation loc;
    	for (int row = 0; row < height("DeathStar"); row++) {
    	    for (int col = 0; col < width("DeathStar"); col++) {
    		loc = deathStarGrid.getLocationByCoordinates(col, row);
    		loc.setLongDescription("DeathStar (" + col + ", " + row + ")");
    		loc.setShortDescription("DeathStar (" + col + ", " + row + ")");
    		loc.setSymbol('.');
    	    }
    	}
    	
    	// Leia
    	loc = deathStarGrid.getLocationByCoordinates(9, 9);
    	PrincessLeiaOrgana leia = new PrincessLeiaOrgana(Team.GOOD, 100, iface, this);
    	entityManager.setLocation(leia, loc);


    	//DarthVader
    	loc = deathStarGrid.getLocationByCoordinates(5, 5);
    	DarthVader darthVader = new DarthVader(Team.EVIL,10000,iface,this);
    	darthVader.setShortDescription("DarthVader");
    	entityManager.setLocation(darthVader, loc);
    	
    	//Stormtroopers
    	loc = deathStarGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
    	StormTrooper st1 = new StormTrooper(Team.EVIL, 100, iface, this);
    	entityManager.setLocation(st1, loc);
    	
    	loc = deathStarGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
    	StormTrooper st2 = new StormTrooper(Team.EVIL, 100, iface, this);
    	entityManager.setLocation(st2, loc);
    	
    	loc = deathStarGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
    	StormTrooper st3 = new StormTrooper(Team.EVIL, 100, iface, this);
    	entityManager.setLocation(st3, loc);
    	
    	loc = deathStarGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
    	StormTrooper st4 = new StormTrooper(Team.EVIL, 100, iface, this);
    	entityManager.setLocation(st4, loc);
    	
    	loc = deathStarGrid.getLocationByCoordinates((int)(Math.random()*10), (int)(Math.random()*10));
    	StormTrooper st5 = new StormTrooper(Team.EVIL, 100, iface, this);
    	entityManager.setLocation(st5, loc);
    	
    	//Millennium Falcon
    	loc = deathStarGrid.getLocationByCoordinates(0, 0);
    	entityManager.setLocation(new MillenniumFalcon(iface), loc);

    	
    }
    
    /**
     * Sets up the Yavin map
     * 
     * @param iface a MessageRenderer to be passed onto newly-created entities
     */
    
    public void initializeYavin(MessageRenderer iface){
    	messageRenderer = iface;
    	SWLocation loc;
    	for (int row = 0; row < height("Yavin"); row++) {
    	    for (int col = 0; col < width("Yavin"); col++) {
    		loc = deathStarGrid.getLocationByCoordinates(col, row);
    		loc.setLongDescription("YavinIV (" + col + ", " + row + ")");
    		loc.setShortDescription("YavinIV (" + col + ", " + row + ")");
    		loc.setSymbol('.');
    	    }
    	}
    	
    	//Mon Mothma
    	loc = yavinGrid.getLocationByCoordinates(1, 0);
    	MonMothma mon = new MonMothma(Team.GOOD, 100, iface, this);
    	entityManager.setLocation(mon, loc);
    	
    	//Admiral Ackbar
    	loc = yavinGrid.getLocationByCoordinates(1, 1);
    	AdmiralAckbar ackbar = new AdmiralAckbar(Team.GOOD, 100, iface, this);
    	entityManager.setLocation(ackbar, loc);
    	
    	//Millennium Falcon
    	loc = yavinGrid.getLocationByCoordinates(0, 0);
    	entityManager.setLocation(new MillenniumFalcon(iface), loc);
    	

    }
    
    /**
     * Sets up the Tatooine map
     * 
     * @param iface a MessageRenderer to be passed onto newly-created entities
     */
    public void initializeTatooine(MessageRenderer iface) {
	messageRenderer = iface;
	SWLocation loc;
	// Set default location string
	for (int row = 0; row < height("Tatooine"); row++) {
	    for (int col = 0; col < width("Tatooine"); col++) {
		loc = tatooineGrid.getLocationByCoordinates(col, row);
		loc.setLongDescription("Tatooine (" + col + ", " + row + ")");
		loc.setShortDescription("Tatooine (" + col + ", " + row + ")");
		loc.setSymbol('.');
	    }
	}

	// BadLands
	for (int row = 5; row < 8; row++) {
	    for (int col = 4; col < 7; col++) {
		loc = tatooineGrid.getLocationByCoordinates(col, row);
		loc.setLongDescription("Tatooine - Badlands (" + col + ", " + row + ")");
		loc.setShortDescription("Tatooine - Badlands (" + col + ", " + row + ")");
		loc.setSymbol('b');
	    }
	}

	//Ben's Hut
	loc = tatooineGrid.getLocationByCoordinates(5, 6);
	loc.setLongDescription("Tatooine - Ben's Hut");
	loc.setShortDescription("Tatooine - Ben's Hut");
	loc.setSymbol('H');

	// Old Ben
	Direction[] patrolmoves = { CompassBearing.EAST, CompassBearing.EAST, CompassBearing.SOUTH, CompassBearing.WEST, CompassBearing.WEST,
		CompassBearing.SOUTH, CompassBearing.EAST, CompassBearing.EAST, CompassBearing.NORTHWEST, CompassBearing.NORTHWEST };
	loc = tatooineGrid.getLocationByCoordinates(4, 5);
	entityManager.setLocation(BenKenobi.getBenKenobi(iface, this, patrolmoves), loc);
	entityManager.setLocation(BenKenobi.getBenKenobi(iface, this, patrolmoves), tatooineGrid.getLocationByCoordinates(4, 6));
	entityManager.setLocation(BenKenobi.getBenKenobi(iface, this, patrolmoves), tatooineGrid.getLocationByCoordinates(5, 6));
	
	// R2D2
	Direction[] r2d2Patrol = { CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST, CompassBearing.EAST,
		CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST, CompassBearing.WEST };
	loc = tatooineGrid.getLocationByCoordinates(3, 7);
	Artoo r2d2 = new Artoo(200, r2d2Patrol, iface, this);
	entityManager.setLocation(r2d2, loc);

	//random droid
	loc = tatooineGrid.getLocationByCoordinates(5, 7);
	SWDroidActor red = new SWDroidActor(Team.GOOD, 200, iface, this);
	red.setShortDescription("Red");
	red.setLongDescription("Red, the R2 unit with a bad motivator. I know how he feels.");
	red.setSymbol("r");
	red.takeDamage(200);
	entityManager.setLocation(red, loc);

	// C3PO
	loc = tatooineGrid.getLocationByCoordinates(6, 7);
	Threepio c3po = new Threepio(200, iface, this);
	entityManager.setLocation(c3po, loc);

	// Luke
	loc = tatooineGrid.getLocationByCoordinates(5, 9);
	Player luke = new Player(Team.GOOD, 100, iface, this);
	luke.setShortDescription("Luke");
	entityManager.setLocation(luke, loc);
	luke.resetMoveCommands(loc);

	
	// Beggar's Canyon 
	for (int col = 3; col < 8; col++) {
	    loc = tatooineGrid.getLocationByCoordinates(col, 8);
	    loc.setShortDescription("Tatooine - Beggar's Canyon (" + col + ", " + 8 + ")");
	    loc.setLongDescription("Tatooine - Beggar's Canyon  (" + col + ", " + 8 + ")");
	    loc.setSymbol('C');
	    loc.setEmptySymbol('='); // to represent sides of the canyon
	}

	// Moisture Farms
	for (int row = 0; row < 10; row++) {
	    for (int col = 8; col < 10; col++) {
		loc = tatooineGrid.getLocationByCoordinates(col, row);
		loc.setLongDescription("Tatooine - Moisture Farm (" + col + ", " + row + ")");
		loc.setShortDescription("Tatooine - Moisture Farm (" + col + ", " + row + ")");
		loc.setSymbol('F');

		// moisture farms have reservoirs
		entityManager.setLocation(new Reservoir(iface), loc);
	    }
	}

	// Scatter some other entities and actors around

	// a empty canteen
	loc = tatooineGrid.getLocationByCoordinates(3, 1);
	entityManager.setLocation(new Canteen(iface, 10, 0), loc);

	// a full canteen
	loc = tatooineGrid.getLocationByCoordinates(6, 6);
	entityManager.setLocation(new Canteen(iface, 10, 10), loc);

	// an oil can
	loc = tatooineGrid.getLocationByCoordinates(1, 5);
	entityManager.setLocation(new OilCan(iface), loc);

	// a lightsaber
	loc = tatooineGrid.getLocationByCoordinates(5, 5);
	entityManager.setLocation(new LightSaber(iface), loc);

	// A blaster 
	loc = tatooineGrid.getLocationByCoordinates(3, 4);
	entityManager.setLocation(new Blaster(iface), loc);
	
	//Millennium Falcon
	loc = tatooineGrid.getLocationByCoordinates(5, 9);
	entityManager.setLocation(new MillenniumFalcon(iface), loc);

	// Tuskens
	TuskenRaider raider = new TuskenRaider(100, "Bilbo", iface, this);
	loc = tatooineGrid.getLocationByCoordinates(4, 3);
	entityManager.setLocation(raider, loc);
	entityManager.setLocation(new GaffiStick(iface), tatooineGrid.getLocationByCoordinates(4, 3));
	entityManager.setLocation(new TuskenRaider(100, "Urrorr'ukr'arhr", iface, this), tatooineGrid.getLocationByCoordinates(2, 3));
	entityManager.setLocation(new TuskenRaider(100, "Rgur'tlr'rauk", iface, this), tatooineGrid.getLocationByCoordinates(2, 5));
	entityManager.setLocation(new TuskenRaider(100, "Gkrror'ukurr", iface, this), tatooineGrid.getLocationByCoordinates(3, 3));
	
	//Aunt Beru
	SWActor beru = new VanillaActor(Team.GOOD, 50, "Aunt Beru", "b", iface, this);
	entityManager.setLocation(beru, tatooineGrid.getLocationByCoordinates(8, 8));
	
	//Uncle Owen
	SWActor owen = new VanillaActor(Team.GOOD, 50, "Uncle Owen", "o", iface, this);
	entityManager.setLocation(owen, tatooineGrid.getLocationByCoordinates(8, 8));
    }

    /**
     * Determine whether a given <code>SWActor a</code> can move in a given
     * direction
     * <code>whichDirection</code>.
     * 
     * @author ram
     * @param a the <code>SWActor</code> being queried.
     * @param whichDirection the <code>Direction</code> if which they want to move
     * @return true if the actor can see an exit in <code>whichDirection</code>, false otherwise.
     */
    public boolean canMove(SWActor a, Direction whichDirection) {
	SWLocation where = (SWLocation) entityManager.whereIs(a); // requires a cast for no reason I can discern. Probably type erasure.
	return where.hasExit(whichDirection);
    }
    

    /**
     * Accessor for the grid.
     * 
     * @author ram
     * @return the grid
     */
    public SWGrid getGrid() {
    	return currentGrid;	
    }
    

    /**
     * Move an actor in a direction.
     * 
     * @author ram
     * @param a
     *            the actor to move
     * @param whichDirection
     *            the direction in which to move the actor
     */
    public void moveEntity(SWActor a, Direction whichDirection) {

	//get the neighboring location in whichDirection
	Location loc = entityManager.whereIs(a).getNeighbour(whichDirection);

	// Base class unavoidably stores superclass references, so do a checked downcast here
	if (loc instanceof SWLocation)
	    //perform the move action by setting the new location to the the neighboring location
	    entityManager.setLocation(a, (SWLocation) entityManager.whereIs(a).getNeighbour(whichDirection));
    }

    /**
     * Returns the <code>Location</code> of a <code>SWEntity</code> in this
     * grid, null if not found.
     * Wrapper for <code>entityManager.whereIs()</code>.
     * 
     * @author ram
     * @param e
     *            the entity to find
     * @return the <code>Location</code> of that entity, or null if it's not in
     *         this grid
     */
    public Location find(SWEntityInterface e) {
	return entityManager.whereIs(e); //cast and return a SWLocation?
    }

    /**
     * This is only here for compliance with the abstract base class's interface
     * and is not supposed to be
     * called.
     */

    @SuppressWarnings("unchecked")
    public EntityManager<SWEntityInterface, SWLocation> getEntityManager() {
	return SWWorld.getEntitymanager();
    }

    /**
     * Returns the <code>EntityManager</code> which keeps track of the
     * <code>SWEntities</code> and
     * <code>SWLocations</code> in <code>SWWorld</code>.
     * 
     * @return the <code>EntityManager</code> of this <code>SWWorld</code>
     * @see {@link #entityManager}
     */
    public static EntityManager<SWEntityInterface, SWLocation> getEntitymanager() {
    return entityManager;
    }
    
    /**
     * Changes the location of an actor and changes the grid displayed
     * 
     * @param actor
     *            the actor to change the location of
     * @param world
     *            an integer that chooses which world to place the actor
     */
    public void setLocation(SWActor actor, int world){
    	SWLocation location;
    	if(world == 0){
    		location = tatooineGrid.getLocationByCoordinates(4, 9);
    		entityManager.setLocation(actor, location);
    		currentGrid = tatooineGrid;
    		SWGridTextInterface.setGrid(currentGrid);
    		return;
    	}
    	
    	else if(world == 1){
    		location = deathStarGrid.getLocationByCoordinates(0, 0);
    		entityManager.setLocation(actor, location);
    		currentGrid = deathStarGrid;
    		SWGridTextInterface.setGrid(currentGrid);
    		return;
    	}
    	
    	
    	else if(world == 2){
    		location = yavinGrid.getLocationByCoordinates(0, 0);
    		entityManager.setLocation(actor, location);
    		currentGrid = yavinGrid;
    		SWGridTextInterface.setGrid(currentGrid);
    		return;
    	}
    }
    
    /**
     * Creates a storm trooper and places it on the grid
     * 
     * @param iface
     *            a MessageRenderer to be passed onto newly-created entities
     * @param x
     *            the x position for the Stormtrooper to be created at
     * @param y               
     *            the y position for the Stormtrooper to be created at
     */
    
    public void createStormTrooper(MessageRenderer iface, int x, int y){
    	SWLocation loc;
    	loc = deathStarGrid.getLocationByCoordinates(x,y);
    	StormTrooper st2 = new StormTrooper(Team.EVIL, 100, iface, this);
    	entityManager.setLocation(st2, loc);
    }

    
    /**
     * returns the boolean wingame, if returns true game is completed, player wins
     */
    
	public boolean isWingame() {
		return wingame;
	}

    /**
     * @param boolean 
     *  if boolean = true player wins the game
     */
    
	public void setWingame(boolean wingame) {
		this.wingame = wingame;
	}

    /**
     * returns the boolean wingame, if returns true game is completed, player loses
     */
	public boolean isLosegame() {
		return losegame;
	}

    /**
     * @param boolean 
     *  if boolean = true player loses the game
     */
	public void setLosegame(boolean losegame) {
		this.losegame = losegame;
	}

    
    
    
}
