package util;

import java.util.*;

/**
 * Remote Object Store implementation with no excepts.
 */
public class RemoteObjectStoreSkeleton implements RemoteObjectStore {
    private Map<String,Set<RemoteObj>> mp;

    @Override
    public Optional<RemoteObj> getObject(String name, long tag) {
        Optional<RemoteObj> result;
        if(mp.containsKey(name)){
            Set<RemoteObj> m = mp.get(name);
            result = m.stream().filter(t -> t.getId()!=tag).findAny();
        }
        else
            result = Optional.empty();
        return result;
    }

    @Override
    public Optional<RemoteObj> getObject(String name) {
        Optional<RemoteObj> result;
        if(mp.containsKey(name)) {
            result = mp.get(name).stream().findAny();
        }
        else {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public boolean insertObject(String name, RemoteObj ro) {
        boolean result = false;
        if(mp.containsKey(name)) {
            mp.get(name).add(ro);
            result = true;
        }
        else {
            Set<RemoteObj> sro = new HashSet<>();
            sro.add(ro);
            mp.put(name,sro);
            result = true;
        }
        return result;
    }

}
