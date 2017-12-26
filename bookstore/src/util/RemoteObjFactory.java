package util;

import java.util.Optional;


/**
 * Interface that describes how to import and export RemoteObjs;
 *
 * @author André Diogo
 * @version 1.0, 22-12-2017
 * @see RemoteObjFactoryImpl
 */
public interface RemoteObjFactory {

    /**
     * Import a stub from a RemoteObj instance.
     * @param ro
     * @return A stub or nothing.
     */
    Optional<Object> importRef(RemoteObj ro);

    /**
     * Export a RemoteObj from an arbitrary Object.
     * @param o
     * @return A RemoteObj or nothing.
     */
    Optional<RemoteObj> exportRef(Object o);
}
