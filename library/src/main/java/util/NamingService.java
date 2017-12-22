package util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;
import pt.haslab.ekit.Log;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO# Log should be trimmed. Need to keep a history of operations to trim properly.
 * NamingService implements the object store interface to allow
 * servers to broadcast availability.
 * <p>
 * Is to be bootstrapped as the first process in charge of recording who is online.
 * <p>
 * Only one naming service will be active at one time and will log all information
 * it receives to ensure consistency.
 * <p>
 * Defaults to port 10000 on localhost.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.2
 * @see RemoteObj
 * @see pt.haslab.ekit.Clique
 * @see ObjectStore
 */
public class NamingService implements ObjectStore {
    private Map<String,Set<RemoteObj>> store;
    private RemoteObj ref;
    private AtomicLong port;
    private int opcount;
    private Log l;
    private ThreadContext tc;
    private List<Address> known;
    private Address me;
    private Transport t;

    /**
     * Constructs a NamingService instance.
     */
    private NamingService() {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        me = new Address("127.0.0.1", 10000);
        t = new NettyTransport();
        tc = new SingleThreadContext("srv-%d", new Serializer());
        this.store = new HashMap<>();
        port = new AtomicLong(10001);
        tc.execute(() -> {
            t.server().listen(me, (c) -> {
                //Register handlers here.
                //c.handler();
            });

            l = new Log("naming");


            //Set handlers for log retrieval here.
            l.handler(long.class,(j,m) -> port.set(m));
            //Wait till log is consistent.
            l.open().join();
        });
    }

    /**
     * Starts up a dedicated NamingService in its own process.
     * @param args Not used currently.
     */
    public static void main(String args[]) {
        NamingService nmsrv = new NamingService();
    }

    /**
     * Insert a RemoteObj instance into the store.
     * @param ro The RemoteObj reference to store (will not be cloned).
     * @return whether the element already existed in the store.
     * @see RemoteObj
     */
    public boolean insertObject(String name, RemoteObj ro) {
        if(store.containsKey(name)) {
            Set<RemoteObj> objstr = store.get(name);
            return objstr.add(ro);
        }
        else {
            store.put(name, new HashSet<>());
            return store.get(name).add(ro);
        }
    }

    /**
     * Return an object of the class supplied with the given tag (Probably has no use,
     * since it doesn't distinguish between host addresses).
     * @param name The class name to look for in the store.
     * @return  an Optional with the RemoteObj or nothing in case none of that class are found.
     * @see RemoteObj
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
     * @see RemoteObj
     */
    public Optional<RemoteObj> getObject(String name) {
        if (store.containsKey(name)) {
            Set<RemoteObj> objs = store.get(name);
            return objs.stream().findAny();
        }
        else
            return Optional.empty();
    }
    /**
     * Operation must be atomic. Getting a unique port should be truly unique
     * even concurrently.
     * @return a unique port number.
     */
    public long getUniquePort() {
        long t = port.incrementAndGet();
        l.append(t);
        return t;
    }

}
