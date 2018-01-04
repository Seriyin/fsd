package util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;
import messaging.util.InsertRemoteObjReply;
import messaging.util.InsertRemoteObjRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Server is an abstract class that encapsulates Catalyst context and network behaviour.
 *
 * Server is immutable.
 */
public abstract class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private final DistObjManager dom;
    private final ThreadContext tc;
    private final List<Address> known;
    private Address me;
    private final Transport t;
    private final Serializer sr;

    protected Serializer getSerializer() {
        return sr;
    }

    protected Transport getTransport() {
        return t;
    }

    protected ThreadContext getThreadContext() {
        return tc;
    }

    protected List<Address> getKnownAddresses() {
        return known;
    }

    protected Address getOwnAddress() {
        return me;
    }

    protected DistObjManager getDom() { return dom; }

    /**
     * Handles server setup. Network connections and thread context startup.
     * <p>
     * Assumes only it and an the default object store exist on the network
     * and updates from there.
     * <p>
     * The serializable classes and handle registration must be done post-construction, before
     * exports or imports.
     * <p>
     * All server classes assume they will talk to a naming service through
     * {@link messaging.util.InsertRemoteObjRequest} and {@link messaging.util.InsertRemoteObjReply}
     * which are registered in the serializer by default.
     * @param name The name through which to registerPayment the server in the naming service.<p>
     *             Should be descriptive of function and will be bagged with other servers registering under the same
     *             name.
     */
    protected Server(String name) {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        t = new NettyTransport();
        sr = new Serializer();
        tc = new SingleThreadContext("srv-%d", sr);
        sr.register(InsertRemoteObjRequest.class);
        sr.register(InsertRemoteObjReply.class);
        dom = new DistObjManager(known,me,t);
    }

    /**
     * Try to get a random user port.
     * <p>
     * User ports are in range 1024-(48127+1024).
     * @throws RuntimeException if 3 sucessive failures
     */
    private void tryPort() {
        boolean success = false;
        for(int i = 0;i<3 && !success; i++) {
            try {
                Random r = new Random();
                me = new Address("127.0.0.1", r.nextInt(48127)+1024);
                t.server().listen(me,this::handlers);
                success=true;
                LOG.debug("Got Port Running");
            }
            catch (Exception e) {e.printStackTrace();}
        }
        if(!success){
            throw new RuntimeException("Can't Open Port");
        }
    }

    /**
     * Use this method to kickstart server enviroment.
     * <p>
     * Specify actual underlying server implementation in the {@link #execute()} method
     * to get called after initial server setup;
     * <p>
     * setup() will attempt to registerPayment serializables ({@link #registerSerializables()}),
     * open a random user port through {@link #tryPort} and finally attempting to
     * registerPayment handlers ({@link #handlers(Connection)}).
     * <p>
     * May except due to port failure {@link #tryPort()}
     */
    protected void setup() {
        registerSerializables();
        //Might except due to port failure
        tc.execute(this::tryPort).join();
    }



    /**
     * Execute the server is up to the underlying implementation of the server.
     * <p>
     * Execution must follow the {@link #setup} method.
     */
    protected abstract void execute();

    /**
     * Register all handlers to listen as server.
     * @param c The open {@link Transport#server()} connection.
     */
    protected abstract void handlers(Connection c);

    /**
     * Register all expected serializable classes specific to the underlying
     * server implementation in the thread-context's serializer.
     */
    protected abstract void registerSerializables();


}
