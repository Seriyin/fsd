package util;

import java.util.*;

/**
 * ObjectStoreSkeleton implements the object store interface.
 * <p>
 * Can be bootstrapped as its separate process using the Clique.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.0
 * @see RemoteObj
 * @see pt.haslab.ekit.Clique
 * @see ObjectStore
 */
public class ObjectStoreSkeleton implements ObjectStore {
    private Map<String,Set<RemoteObj>> store;

    /**
     * Constructs an ObjectStore instance.
     */
    public ObjectStoreSkeleton() {
        this.store = new HashMap<>();
    }

    /**
     * Starts up a dedicated ObjectStore in its own process.
     * @param args Not used currently.
     */
    public void main(String args[]) {
        ObjectStore objstr = new ObjectStoreSkeleton();
    }

    /**
     * Insert a RemoteObj instance into the store.
     * @param ro The RemoteObj reference to store (will not be cloned).
     * @return whether the element already existed in the store.
     */
    public boolean insertObject(RemoteObj ro) {
        if(store.containsKey(ro.getCls())) {
            Set<RemoteObj> objstr = store.get(ro.getCls());
            return objstr.add(ro);
        }
        else {
            store.put(ro.getCls(), new HashSet<>());
            return store.get(ro.getCls()).add(ro);
        }
    }

    /**
     * Return an object of the class supplied with the given tag (Probably has no use,
     * since it doesn't distinguish between host addresses).
     * @param name The class name to look for in the store.
     * @return  an Optional with the RemoteObj or nothing in case none of that class are found.
     */
    public Optional<RemoteObj> getObject(String name, long tag) {
        if (store.containsKey(name)) {
            Set<RemoteObj> objs = store.get(name);
            return objs.stream().filter(r -> r.getId() == tag).findFirst();
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * Return a random object of the class supplied
     * @param name The class name to look for in the store
     * @return  an Optional with the RemoteObj or nothing in case none of that class are found.
     */
    public Optional<RemoteObj> getObject(String name) {
        if (store.containsKey(name)) {
            Set<RemoteObj> objs = store.get(name);
            return objs.stream().findAny();
        }
        else
            return Optional.empty();
    }



}
