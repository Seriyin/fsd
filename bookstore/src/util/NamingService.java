package util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;
import pt.haslab.ekit.Log;

import java.util.*;

/**
 * TODO# Log should be trimmed. Need to keep a history of operations to trim properly.
 * NamingService implements the object store interface to allow
 * servers to broadcast availability.
 * <p>
 * Is to be bootstrapped as the first process in charge of recording who is online.
 * <p>
 * Only one naming service will be active at one time for now and will log all
 * information it receives to ensure consistency.
 * <p>
 * Defaults to port 10000 on localhost.
 * @see RemoteObj
 * @see pt.haslab.ekit.Clique
 * @see ObjectStore
 */
public class NamingService implements ObjectStore,TransactionsManager {
    private Map<String,Set<RemoteObj>> store;
    private RemoteObj ref;
    private Log log;
    private Log coord;
    private ThreadContext tc;
    private List<Address> known;
    private Address me;
    private Transport t;


    /**
     * Constructs a NamingService instance on port 10000, localhost.
     */
    private NamingService() {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        me = new Address("127.0.0.1", 10000);
        t = new NettyTransport();
        tc = new SingleThreadContext("srv-%d", new Serializer());
        this.store = new HashMap<>();
        tc.execute(() -> {
            openLogs();

            t.server().listen(me, this::registerHandlers);


        });
    }

    /**
     * #TODO logging handlers.
     * Registers log handlers, then opens logs to ensure consistency.
     */
    private void openLogs() {
        log = new Log("naming");
        coord = new Log("coord");

        //Set messaging for NamingService's log.
        log.handler(long.class,(j,m) -> {});
        //Wait till log is consistent.
        log.open().join();

        //Set messaging for TransactionManager's log.
        coord.handler(long.class,(j,m) -> {});
        //Wait till coord is consistent.
        coord.open().join();
    }

    /**
     * Register handlers for server connection.
     * @param connection The connection naming service listens on.
     */
    private void registerHandlers(Connection connection) {
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

}
