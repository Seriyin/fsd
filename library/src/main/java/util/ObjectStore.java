package util;

import java.util.Optional;

/**
 * ObjectStore is an interface that describes how to store and recover RemoteObj instances
 * for project-wide consultation.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.0
 * @see RemoteObj
 * @see pt.haslab.ekit.Clique
 */
public interface ObjectStore {

    Optional<RemoteObj> getObject(String name, long tag);
    Optional<RemoteObj> getObject(String name);
    boolean insertObject(RemoteObj ro);
}