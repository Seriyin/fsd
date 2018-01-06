package pt.um.bookstore.util;


import io.atomix.catalyst.transport.Connection;

import java.util.Optional;

/**
 * Interface that describes how to import and export RemoteObjs, that
 * factories of RemoteObj must adhere to.
 * <p>
 * They must implement export and import.
 * <p>
 * The RemoteObjFactory is more specific and includes connection specifics.
 * @see RemoteObjFactoryImpl
 */
public interface RemoteObjFactory {

    Optional<? extends Stub> importRef(RemoteObj ref, Connection c);
    Optional<RemoteObj> exportRef(Object obj, ObjectStore<Object> objstr);

}
