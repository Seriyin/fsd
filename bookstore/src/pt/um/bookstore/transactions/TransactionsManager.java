package pt.um.bookstore.transactions;

import io.atomix.catalyst.serializer.CatalystSerializable;
import pt.um.bookstore.util.RemoteObj;

/**
 * TransactionsManager will be responsible for ensuring book buys are atomic.
 * <p>
 * Should be more general as to ensure arbitrary operations occur atomically.
 */
public interface TransactionsManager {

    /**
     * Create transaction-context and send it to the client.
     * <p>
     * Works synchronously for obvious reasons through use of
     * {@link io.atomix.catalyst.transport.Connection#sendAndReceive(Object)}.
     * @return a transaction id or -1 if error.
     */
    long begin();

    /**
     * Implicit process registration into this transaction-context.
     * @param xid The transaction id.
     * @param ro reference of resource to contact.
     * @param logged object to log.
     * @param <T> type of object to log.
     */
    <T extends CatalystSerializable> void heartbeat(long xid, RemoteObj ro, T logged);

    /**
     * Client request to prepare resources for commit.
     * <p>
     * Initializes first stage of 2PC.
     * @param xid The transaction id.
     */
    void prepare(long xid);

    /**
     * Handle prepared response from resource.
     * @param xid The transaction id.
     * @param ro remote reference of resource that's prepared.
     */
    void prepared(long xid, RemoteObj ro);

    /**
     *  Handle abort request from a resource.
     *  <p>
     *  Will result in abort request to all resources
     *  and inform client.
     *  @param xid The transaction id.
     */
    void abort(long xid);

}