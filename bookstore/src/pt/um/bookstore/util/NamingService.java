package pt.um.bookstore.util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;
import pt.um.bookstore.handlers.transactions.CoordAbortRequestHandler;
import pt.um.bookstore.handlers.util.GetRequestHandler;
import pt.um.bookstore.handlers.util.RegisterRequestHandler;
import pt.um.bookstore.messaging.transactions.*;
import pt.um.bookstore.messaging.util.GetRemoteObjReply;
import pt.um.bookstore.messaging.util.GetRemoteObjRequest;
import pt.um.bookstore.messaging.util.InsertRemoteObjReply;
import pt.um.bookstore.messaging.util.InsertRemoteObjRequest;
import pt.um.bookstore.transactions.TransactionsManager;
import pt.um.bookstore.transactions.TransactionsManagerSkeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * TODO# Log should be trimmed. Need to keep a history of operations to trim properly.
 * NamingService implements the object {@link RemoteObjectStore} interface to allow
 * servers to broadcast availability.
 * <p>
 * Is to be bootstrapped as the first process in charge of recording who is online.
 * <p>
 * Only one naming service will be active at one time for now and will log all
 * information it receives to ensure consistency.
 * <p>
 * Is a facade of two services, {@link RemoteObjectStore} and {@link TransactionsManager}.
 * <p>
 * Defaults to port 10000 on localhost.
 * @see RemoteObj
 * @see pt.haslab.ekit.Clique
 * @see RemoteObjectStore
 */
public class NamingService implements RemoteObjectStore, TransactionsManager
{
    private RemoteObjectStore store;
    private TransactionsManager tm;
    private DistObjManager dom;
    private RemoteObj ref;
    private ThreadContext tc;
    private List<Address> known;
    private Address me;
    private Transport t;


    /**
     * Constructs a NamingService instance on port 10000, localhost.
     * @throws RuntimeException when it fails to open port or export remote reference.
     */
    private NamingService() {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        me = new Address("127.0.0.1", 10000);
        t = new NettyTransport();
        tc = new SingleThreadContext("srv-%d", new Serializer());
        dom = new DistObjManager(me, t);
        ref = dom.exportRef(this).orElseThrow(RuntimeException::new);
        registerSerializables();
        tc.execute(() -> {
            //Object creation also start log rebuild.
            this.store = new RemoteObjectStoreSkeleton(dom);
            this.tm = new TransactionsManagerSkeleton(dom);
            t.server().listen(me, this::registerHandlers);
        });
    }


    /**
     * Registers serializables for the naming service.
     */
    private void registerSerializables() {
        Serializer sr = tc.serializer();

        //Remote object pt.um.bookstore.store messages.
        sr.register(InsertRemoteObjRequest.class);
        sr.register(InsertRemoteObjReply.class);
        sr.register(GetRemoteObjRequest.class);
        sr.register(GetRemoteObjReply.class);

        //Transaction coordinator messages.
        sr.register(AbortRequest.class);
        sr.register(BeginTransactionRequest.class);
        sr.register(BeginTransactionReply.class);
        sr.register(CommitRequest.class);
        sr.register(HeartbeatRequest.class);
        sr.register(PreparedRequest.class);
        sr.register(PrepareRequest.class);
    }

    /**
     * Register handlers for server connection.
     * @param c The connection naming service listens on.
     */
    private void registerHandlers(Connection c) {
        c.handler(InsertRemoteObjRequest.class, new RegisterRequestHandler(store));
        c.handler(GetRemoteObjRequest.class, new GetRequestHandler(store));
        c.handler(AbortRequest.class, new CoordAbortRequestHandler(tm));
    }

    /**
     * Starts up a dedicated NamingService in its own process.
     * @param args Not used currently.
     */
    public static void main(String args[]) {

        NamingService nmsrv = new NamingService();
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            while(!bf.readLine().equals("quit"));
        }
        catch (IOException e) {
            //ups
        }
    }

    /**
     * Insert a RemoteObj instance into the store.
     * @param ro The RemoteObj reference to store (will not be cloned).
     * @return whether the element already existed in the store.
     * @see RemoteObj
     */
    public boolean insertObject(String name, RemoteObj ro) {
        return store.insertObject(name,ro);
    }

    /**
     * Return an object of the class supplied with the given tag (Probably has no use,
     * since it doesn't distinguish between host addresses).
     * @param name The class name to look for in the store.
     * @return  an Optional with the RemoteObj or nothing in case none of that class are found.
     * @see RemoteObj
     */
    public Optional<RemoteObj> getObject(String name, long tag) {
        return store.getObject(name, tag);
    }

    /**
     * Return a random object of the class supplied
     * @param name The class name to look for in the store
     * @return  an Optional with the RemoteObj or nothing in case none of that class are found.
     * @see RemoteObj
     */
    public Optional<RemoteObj> getObject(String name) {
        return store.getObject(name);
    }


    /**
     * Create transaction-context and send it to the client.
     * <p>
     * Works synchronously for obvious reasons through use
     * of {@link Connection#sendAndReceive(Object)}.
     *
     * @return a transaction id or -1 if error.
     */
    @Override
    public long begin()
    {
        return tm.begin();
    }

    /**
     * Implicit process registration into this transaction-context.
     *
     * @param xid    The transaction id.
     * @param ro     reference of resource to contact.
     * @param logged object to log.
     */
    @Override
    public <T extends CatalystSerializable> void heartbeat(long xid, RemoteObj ro, T logged)
    {
        tm.heartbeat(xid,ro,logged);
    }

    /**
     * Client request to prepare resources for commit.
     * <p>
     * Initializes first stage of 2PC.
     *
     * @param xid The transaction id.
     */
    @Override
    public void prepare(long xid)
    {
        tm.prepare(xid);
    }

    /**
     * Handle prepared response from resource.
     *
     * @param xid The transaction id.
     * @param ro  remote reference of resource that's prepared.
     */
    @Override
    public void prepared(long xid, RemoteObj ro)
    {
        tm.prepared(xid, ro);
    }

    /**
     * Handle abort request from a resource.
     * <p>
     * Will result in abort request to all resources and inform client.
     *
     * @param xid The transaction id.
     */
    @Override
    public void abort(long xid)
    {
        tm.abort(xid);
    }
}
