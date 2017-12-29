package util;

import java.util.Optional;

/**
 * Importer defines a single method that all objects that work on
 * remote object importing must adhere too.
 *
 * @author André Diogo <redan.blue27@gmail.com>
 * @version 1.0, 29-12-2017
 */
public interface Importer {

    Optional<? extends Stub> importRef(RemoteObj ro);
}
