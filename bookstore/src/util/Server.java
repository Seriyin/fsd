package util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;
import messaging.RegisterReply;
import messaging.RegisterRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;


/**
 * Server is an abstract class that encapsulates Catalyst context and network behaviour.
 * @author André Diogo
 * @version 1.2, 26-12-2017
 */
public abstract class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private ThreadContext tc;
    private RegisterRequest rq;
    private List<Address> known;
    private DistObjManager dom;
    private Address me;
    private Transport t;
    private Serializer sr;

    protected Serializer getSr() {
        return sr;
    }

    protected ThreadContext getTc() {
        return tc;
    }

    protected List<Address> getKnown() {
        return known;
    }

    protected Address getAddress() {
        return me;
    }

    /**
     * Handles server setup. Network connections and thread context startup.
     * <p>
     * Assumes only it and an object store exist on the network and updates from there.
     * <p>
     * The serializable classes and handle registration must be done post-construction, before
     * exports or imports.
     * <p>
     * All server classes assume they will talk to a naming service through {@link RegisterRequest} and
     * {@link RegisterReply}.
     * @param name The name through which to register the server in the naming service.
     *             Should be descriptive of function and will be bagged with other servers registering under the same
     *             name.
     */
    protected Server(String name) {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        t = new NettyTransport();
        sr = new Serializer();
        tc = new SingleThreadContext("srv-%d", sr);
        dom = new DistObjManager(known, me, t);
        sr.register(RegisterRequest.class);
        sr.register(RegisterReply.class);
    }

    /**
     * Try to get a random user port.
     * <p>
     * User ports are in range 1024-(48127+1024).
     * @throws RuntimeException if 3 sucessive failures
     */
     private void tryPort() {
        boolean success = false;
        for(int i=0;i<3 && success==false;i++) {
            try {
                Random r = new Random();
                me = new Address("127.0.0.1", r.nextInt(48127)+1024);
                t.server().listen(me,this::handlers);
                success=true;
                LOG.debug("Got Port Running");
            }
            catch (Exception e) {e.printStackTrace();}
        }
        if(success==false){
            throw new RuntimeException("Can't Open Port");
        }
    }

    /**
     * Use this method to kickstart server enviroment.
     * <p>
     * Specify actual underlying server implementation in the {@link #execute()} method
     * to get called after initial server setup;
     * <p>
     * setup() will attempt to register serializables ({@link #registerSerializables()}),
     * open a random user port through {@link #tryPort} and finally attempting to
     * register handlers ({@link #handlers(Connection)}).
     * <p>
     * May except due to port failure {@link #tryPort()}
     */
    protected void setup() {
        registerSerializables();
        tc.execute(() -> {
            //Might except due to port failure
            tryPort();
        }).join();
    }

    /**
     * Send a register request to the first known address, which defaults to
     * the pre-established naming service (canonically localhost:10000).
     * <p>
     * This request will be tried 3 times in a synchronized fashion
     * and may except after failure with an unchecked exception.
     * <p>
     * Does not require a special handler to be registered.
     * Will use {@link Connection#sendAndReceive(Object)}
     * @param rq The request to send.
     */
    protected void sendRegisterRequest(RegisterRequest rq) {
        this.rq = rq;
        t.client().connect(known.get(0)).thenAccept(this::syncedRegistration);
    }

    /**
     * This method attempts to register at the naming service connected thrice.
     * <p>
     * If it fails throws unchecked exception.
     * @param connection
     */
    private void syncedRegistration(Connection connection) {
        boolean hasSucceeded = false;
        for(int i=0;i<3 && hasSucceeded==false;i++) {
            CompletableFuture<RegisterReply> r = connection.sendAndReceive(rq);
            hasSucceeded = r.join().hasSucceeded();
        }
        if (hasSucceeded==false) {
            throw new RuntimeException("Failed to register at naming service");
        }
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
