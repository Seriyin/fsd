package pt.um.bookstore.util;

import java.util.Optional;

/**
 * Importer defines a single method that all objects that work on
 * remote object importing must adhere too.
 */
public interface Importer {

    /**
     * Imports a stub from a RemoteObj.
     * @param ro The RemoteObj to generate stub from
     * @return An optional Stub. Caller must cast accordingly to desired stub.
     * @see Stub
     * @see RemoteObj
     */
    Optional<? extends Stub> importRef(RemoteObj ro);
}
