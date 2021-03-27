package starwars.entities;

import starwars.Capability;

/**
 * Interface for SWEntities fillable with water
 * 
 * All fillable objects must have capability FILLABLE.
 * @author Robert Merkel
 * @see {@link starwars.Capability}
 */
public interface Fillable {
    /**
     * Fill this SWEntity with water
     */
    void fill();

    void drink();

    Capability filledWith();
}
