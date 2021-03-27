package starwars.entities.actors;

import starwars.SWActor;

public interface SWOwnerInterface {
    boolean hasOwner();

    void setOwner(SWActor newOwner);
}
