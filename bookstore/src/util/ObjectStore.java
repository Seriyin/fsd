package util;

import java.util.Optional;

/**
 * ObjectStore is an interface that describes how to store and
 * recover RemoteObj instances for project-wide consultation.
 * @see RemoteObj
 * @see pt.haslab.ekit.Clique
 */
public interface ObjectStore {

    /**
     * Get an object reference by unique-name and tag.
     * @param name An unique string identifying a set of objects.
     * @param tag The tag of a specific object in that set.
     * @return The reference or empty if it does not exist in the store.
     */
    Optional<RemoteObj> getObject(String name, long tag);

    /**
     * Get an object reference by unique-name.
     * @param name An unique string identifying a set of objects.
     * @return The reference to a random object of that set,
     *          or empty if no reference exists.
     */
    Optional<RemoteObj> getObject(String name);

    /**
     * Insert an object into a unique-name's set.
     * <p>
     * Can be used to store objects whose functionality is independant of the actual
     * machine in which they are located and any such object is capable of serving
     * the same kinds of requests.
     * @param name Unique-name under which to insert the reference.
     * @param ro Reference to insert under name.
     * @return whether the insertion succeeded.
     */
    boolean insertObject(String name, RemoteObj ro);
}