package util;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        boolean result;
        if(mp.containsKey(name)) {
            result = mp.get(name).add(ro);
        }
        else {
            Set<RemoteObj> sro = new HashSet<>();
            sro.add(ro);
            result = mp.put(name,sro)!=null;
        }
        return result;
    }

}
