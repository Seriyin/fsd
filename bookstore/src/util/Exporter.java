package util;

import java.util.Optional;

/**
 * Exporter defines a single method that all objects that work on
 * remote object exporting must adhere too.
 *
 * @author André Diogo <redan.blue27@gmail.com>
 * @version 1.0, 29-12-2017
 */
public interface Exporter {

   Optional<RemoteObj> exportRef(Object obj);
}
