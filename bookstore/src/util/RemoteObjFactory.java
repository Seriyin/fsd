package util;

/**
 * Interface that describes how to import and export RemoteObjs, that
 * factories of RemoteObj must adhere to.
 *
 * They must implement export and import.
 *
 * @author André Diogo
 * @version 1.1, 29-12-2017
 * @see RemoteObjFactoryImpl
 */
public interface RemoteObjFactory extends Importer,Exporter {}
