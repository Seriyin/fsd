package pt.um.bookstore.util;

import pt.haslab.ekit.Log;

import java.util.*;

/**
 * Remote Object Store implementation with no excepts.
 */
public class RemoteObjectStoreSkeleton extends Skeleton implements RemoteObjectStore {
    private final Map<String,Set<RemoteObj>> mp;
    private final DistObjManager dom;
    private final Log log;

    public RemoteObjectStoreSkeleton(DistObjManager dom)
    {
        mp = new HashMap<>();
        this.dom = dom;
        log = new Log("naming");
        openLog();
        setRef(dom.exportRef(this).orElse(null));
    }

    public void openLog()
    {
        //Set pt.um.bookstore.messaging for NamingService's log.
        log.handler(long.class,(j,m) -> {});
        //Wait till log is consistent.
        log.open().join();

    }

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
