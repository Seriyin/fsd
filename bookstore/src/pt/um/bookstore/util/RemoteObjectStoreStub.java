package pt.um.bookstore.util;

import io.atomix.catalyst.transport.Connection;
import pt.um.bookstore.messaging.util.GetRemoteObjReply;
import pt.um.bookstore.messaging.util.GetRemoteObjRequest;
import pt.um.bookstore.messaging.util.InsertRemoteObjReply;
import pt.um.bookstore.messaging.util.InsertRemoteObjRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * RemoteObjectStoreStub will cache the latest RemoteObj retrieved.
 * <p>
 * It is a class that fires off requests to a known object store
 * to store or retrieve unique-named objects.
 */
public class RemoteObjectStoreStub extends Stub implements RemoteObjectStore {
    private Map<String,RemoteObj> cached;


    public RemoteObjectStoreStub(RemoteObj b, Connection c) {
        super(b,c);
        cached = new HashMap<>();
    }

    @Override
    public Optional<RemoteObj> getObject(String name, long tag) {
        Optional<RemoteObj> ro;
        if(cached.containsKey(name)) {
            RemoteObj cache = cached.get(name);
            if(cache.getId()==tag) {
                ro = Optional.of(cache);
            }
            else {
                GetRemoteObjRequest rq = new GetRemoteObjRequest(getRef(), name, tag);
                ro = getConnection().<GetRemoteObjRequest, GetRemoteObjReply>sendAndReceive(rq)
                                    .join().getRemoteObj();
            }
        }
        else {
            GetRemoteObjRequest rq = new GetRemoteObjRequest(getRef(),name,tag);
            ro = getConnection().<GetRemoteObjRequest, GetRemoteObjReply>sendAndReceive(rq)
                                .join().getRemoteObj();
        }
        return ro;
    }

    @Override
    public Optional<RemoteObj> getObject(String name)
    {
        Optional<RemoteObj> ro;
        if(cached.containsKey(name)) {
            RemoteObj cache = cached.get(name);
            ro = Optional.of(cache);
        }
        else {
            GetRemoteObjRequest rq = new GetRemoteObjRequest(getRef(),name);
            ro = getConnection().<GetRemoteObjRequest, GetRemoteObjReply>sendAndReceive(rq)
                         .join().getRemoteObj();
        }
        return ro;
    }

    @Override
    public boolean insertObject(String name, RemoteObj ro)
    {
        InsertRemoteObjRequest rq = new InsertRemoteObjRequest(ro, name);
        return getConnection().<InsertRemoteObjRequest, InsertRemoteObjReply>
                                       sendAndReceive(rq)
                              .join()
                              .hasSucceeded();
    }
}
