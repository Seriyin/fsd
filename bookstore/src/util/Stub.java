package util;

import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.transport.Client;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;


/**
 * Stub encapsulates network behaviour for a stub of a remote object.
 * <p>
 * Stubs get hold of {@link ThreadContext} to execute callbacks
 * over a {@link Client} on a {@link Transport}.
 * @see RemoteObj
 */
public abstract class Stub {
    private ThreadContext tc;
    private Connection c;
    private RemoteObj ro;


    /**
     * Constructor for Stub takes in RemoteObj and Transport.
     * @param ro RemoteObj of the actual object.
     * @param c Connection over which to send requests.
     */
    protected Stub(RemoteObj ro, Connection c) {
        tc = ThreadContext.currentContext();
        this.c = c;
        this.ro = ro;
    }

    protected Connection getConnection() {
        return c;
    }

    protected RemoteObj getRef() {
        return ro;
    }
}
