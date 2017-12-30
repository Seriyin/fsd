package util;

import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.transport.Client;
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
    private Transport t;
    private RemoteObj ro;


    /**
     * Constructor for Stub takes in RemoteObj and Transport.
     * @param ro RemoteObj of the actual object.
     * @param t Transport over which to connect.
     */
    protected Stub(RemoteObj ro, Transport t) {
        tc = ThreadContext.currentContext();
        this.t = t;
        this.ro = ro;
    }

}
