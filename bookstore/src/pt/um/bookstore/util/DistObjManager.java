package pt.um.bookstore.util;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import pt.um.bookstore.messaging.util.InsertRemoteObjReply;
import pt.um.bookstore.messaging.util.InsertRemoteObjRequest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * TODO store and manage connections.
 * Each process needs to manage its available distributed objects, as well as
 * leases on remote objects. This class is in charge of this.
 * Imports and Exports RemoteObjects via a RemoteObjFactoryImpl.
 * <p>
 * Right now it assumes a Address + Known Addresses.
 * @see RemoteObjFactoryImpl
 */
public final class DistObjManager implements Importer,Exporter {
    private RemoteObjFactory rof;
    private ObjectStore<Object> objstr;
    private Map<Address, Connection> cm;
    private Connection transactionManager;
    private Connection remoteObjectStore;
    private Transport t;
    private List<Address> known;
    private Address me;

    /**
     * Not using clique at all for dynamic networking.
     * <p>
     * Simple parameterized constructor receives
     * the Addresses that are known to the DistObjManager current process
     * and the actual Address of the process it's in.
     * <p>
     * {@literal We assume each process -> one server and/or client.}
     * @param known List of known addresses.
     * @param me The process's own address.
     * @param t The transport with which to work on setting up connections.
     */
    public DistObjManager(List<Address> known, Address me, Transport t) {
        this.me = me;
        this.known = known;
        this.t = t;
        objstr = new ObjectStoreSkeleton<>();
        cm = new HashMap<>();
        rof = new RemoteObjFactoryImpl(me);
    }

    /**
     * Not using clique at all for dynamic networking.
     * <p>
     * Receives the actual Address of the process it's in.
     * <p>
     * Assumes known addresses consist only of
     * the default naming service.
     * @param me The process's own address.
     * @param t The transport with which to work on setting up connections.
     */
    public DistObjManager(Address me, Transport t) {
        this.me = me;
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        this.t = t;
        objstr = new ObjectStoreSkeleton<>();
        cm = new HashMap<>();
        rof = new RemoteObjFactoryImpl(me);
    }

    public Connection getConnection(Address address)
    {
        return cm.getOrDefault(address,null);
    }

    /**
     * TODO Reference counting and connection handling also needs to be done.
     * Handles exporting a RemoteObj from the given object.
     * <p>
     * Delegates actual exporting to the RemoteObjFactoryImpl
     * @param obj The object to export
     * @return the RemoteObj exported or empty.
     * @see RemoteObj
     * @see RemoteObjFactory
     */
    public Optional<RemoteObj> exportRef(Object obj) {
        return rof.exportRef(obj, objstr);
    }


    /**
     * TODO Reference counting and connection handling
     * Handles importing a stub from the given object reference.
     * <p>
     * Delegates actual importing to the RemoteObjFactoryImpl
     * @param b The object reference.
     * @return A stub of the object pointed to or empty.
     * @see RemoteObj
     * @see RemoteObjFactory
     */
    public Optional<? extends Stub> importRef(RemoteObj b) {
        Optional<? extends Stub> result;
        Address ad = b.getAddress();
        if(cm.containsKey(ad)) {
            Connection c = cm.get(ad);
            result = rof.importRef(b,c);
        }
        else {
            result = Optional.empty();
        }
        return result;
    }

    /**
     * Returns a clone of a skeleton pointed to by a RemoteObj.
     * <p>
     * Has to be done via reflection. (or manual monomorphization).
     * <p>
     * Excepts out the wazoo.
     * @param b the reference to be looked-up.
     * @return A clone of a skeleton or empty.
     * @see RemoteObj
     * @throws InvocationTargetException Due to calling clone via reflection.
     * @throws IllegalAccessException Due to clone being possibly not public.
     * @throws NoSuchMethodException If clone does not exist as a method.
     */
    public Optional<Object> importCopy(RemoteObj b)
            throws InvocationTargetException,
                   IllegalAccessException,
                   NoSuchMethodException
    {
        String cls = b.getCls();
        Optional<Object> op = objstr.getObject(b.getCls(),b.getId());
        if(op.isPresent()) {
            Object obj = op.get();
            //Get current class (might be Object, not entirely helpful)
            //Use as last resort.
            Class co = obj.getClass();
            //Check if it has a declared class that is more specific
            //Use that one first.
            Class sup = co.getDeclaringClass();
            Method clone;
            //Try and invoke a clone.
            if (sup!=null) {
                clone = sup.getDeclaredMethod("clone");
            }
            else {
                    clone = co.getDeclaredMethod("clone");
            }
            return Optional.of(clone.invoke(null));
        }
        return Optional.empty();
    }

    /**
     * TODO Should start a lease on the remote object pt.um.bookstore.store.
     * Send a register request to the first known address, which defaults to
     * the pre-established naming service (canonically localhost:10000).
     * <p>
     * This request will be tried 3 times in a synchronized fashion
     * and may except after failure with an unchecked exception.
     * <p>
     * Does not require a special handler to be registered.
     * Will use {@link Connection#sendAndReceive(Object)}
     * @param rq The request to send.
     * @throws RuntimeException if it fails to register in naming service.
     */
    public void sendRegisterRequest(InsertRemoteObjRequest rq) {
        Address k = known.get(0);
        Connection cn = t.client().connect(k).join();
        cm.put(k,cn);
        transactionManager = cn;
        remoteObjectStore = cn;
        syncedRegistration(cn,rq);
    }

    /**
     * This method attempts to register at the naming service connected thrice.
     * <p>
     * If it fails throws unchecked exception.
     * @param connection Connection to use for sending and receiving.
     * @param rq The Request to send.
     * @throws RuntimeException if it fails to register in naming service.
     */
    private void syncedRegistration(Connection connection, InsertRemoteObjRequest rq) {
        boolean hasSucceeded = false;
        for(int i = 0;i<3 && !hasSucceeded; i++) {
            CompletableFuture<InsertRemoteObjReply> r = connection.sendAndReceive(rq);
            hasSucceeded = r.join().hasSucceeded();
        }
        if (!hasSucceeded) {
            throw new RuntimeException("Failed to register at naming service");
        }
    }

    public Connection getTransactionsManager()
    {
        return transactionManager;
    }

    public Connection getRemoteObjectStore()
    {
        return remoteObjectStore;
    }
}