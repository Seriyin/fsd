package util;

import java.util.Optional;

/**
 * Exporter defines a single method that all objects that work on
 * remote object exporting must adhere too.
 *
 * @author André Diogo <redan.blue27@gmail.com>
 * @version 1.1, 29-12-2017
 */
public interface Exporter {

    /**
     * Export a RemoteObj from an Object instance.
     * @param obj the Object from which to generate the remote reference.
     * @return An optional remote reference
     * @see RemoteObj
     */
    Optional<RemoteObj> exportRef(Object obj);
}
