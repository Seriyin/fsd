package pt.um.bookstore.util;

import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.transport.Connection;
import pt.um.bookstore.transactions.TransactionsManager;
import pt.um.bookstore.transactions.TransactionsManagerStub;

import java.util.Optional;

/**
 * Facade that contains both a TransactionsManager and RemoteObjectStore.
 */
public class NamingServiceStub
        extends Stub
        implements TransactionsManager,
                   RemoteObjectStore
{
    private TransactionsManager tm;
    private RemoteObjectStore ros;

    public NamingServiceStub(RemoteObj b, Connection c)
    {
        super(b,c);
        tm = new TransactionsManagerStub(b, c);
        ros = new RemoteObjectStoreStub(b,c);
    }

    /**
     * Get an object reference by unique-name and tag.
     *
     * @param name An unique string identifying a set of objects.
     * @param tag  The tag of a specific object in that set.
     *
     * @return The reference or empty if it does not exist in the store.
     */
    @Override
    public Optional<RemoteObj> getObject(String name, long tag)
    {
        return ros.getObject(name,tag);
    }

    /**
     * Get an object reference by unique-name.
     *
     * @param name An unique string identifying a set of objects.
     *
     * @return The reference to a random object of that set, or empty if no reference exists.
     */
    @Override
    public Optional<RemoteObj> getObject(String name)
    {
        return ros.getObject(name);
    }

    /**
     * Insert an object into a unique-name's set.
     * <p>
     * Can be used to store objects whose functionality is independent of the actual machine in which they are located
     * and any such object is capable of serving the same kinds of requests.
     *
     * @param name Unique-name under which to insert the reference.
     * @param ro   Reference to insert under name.
     *
     * @return whether the insertion succeeded.
     */
    @Override
    public boolean insertObject(String name, RemoteObj ro)
    {
        return ros.insertObject(name, ro);
    }

    /**
     * Create transaction-context and send it to the client
     *
     * @return whether the transaction context was able to be created.
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
        tm.prepared(xid,ro);
    }


    /**
     * Handle abort from a resource.
     * <p>
     * Must abort all resources and inform client.
     *
     * @param xid The transaction id.
     */
    @Override
    public void abort(long xid)
    {
        tm.abort(xid);
    }
}
