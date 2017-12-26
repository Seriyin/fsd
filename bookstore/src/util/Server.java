package util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Server is an abstract class that encapsulates Catalyst context and network behaviour.
 * @author André Diogo
 * @version 1.1, 26-12-2017
 */
public abstract class Server {
    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private ThreadContext tc;
    private List<Address> known;
    private DistObjManager dom;
    private Address me;
    private Transport t;


    /**
     * Handles server setup. Network connections and thread context startup.
     * <p>
     * Assumes only it and an object store exist on the network and updates from there.
     * <p>
     * The address and handle registration must be done post-construction, before
     * exports or imports.
     */
    protected Server() {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10000));
        t = new NettyTransport();
        tc = new SingleThreadContext("srv-%d", new Serializer());
        dom = new DistObjManager(known, me);
    }

    /**
     * Try to get a random user port.
     * <p>
     * User ports are in range 1024-(48127+1024).
     * @throws RuntimeException if 3 sucessive failures
     */
     protected void tryPort() {
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
     * Execute the server is up to the underlying implementation of the server.
     */
    protected abstract void execute();

    /**
     * Register all handlers to listen as server.
     * @param c
     */
    protected abstract void handlers(Connection c);
}
