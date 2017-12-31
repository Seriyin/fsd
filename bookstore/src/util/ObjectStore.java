package util;

import java.util.Optional;

/**
 * ObjectStore is an interface that describes how to store and
 * recover Objects instances into a structured container.
 */
public interface ObjectStore<T> {


    /**
     * Get an object reference by unique-name and tag.
     * @param name An unique string identifying a set of objects.
     * @param tag The tag of a specific object in that set.
     * @return The reference or empty if it does not exist in the store.
     */
    Optional<T> getObject(String name, long tag);

    /**
     * Insert an object into a unique-name's set.
     * <p>
     * Can be used to store objects with both addressing and uniqueness.
     * <p>
     * @param name Unique-name under which to insert the reference.
     * @param tag Unique-number under which to insert the reference.
     * @param ro Reference to insert under name.
     * @return whether the insertion succeeded.
     */
    boolean insertObject(String name, long tag, T ro);

}
