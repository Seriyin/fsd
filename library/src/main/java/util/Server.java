package util;

import io.atomix.catalyst.concurrent.SingleThreadContext;
import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Transport;
import io.atomix.catalyst.transport.netty.NettyTransport;

import java.util.ArrayList;
import java.util.List;

public abstract class Server {
    private ThreadContext tc;
    private List<Address> known;
    private DistObjManager dom;
    private Address me;
    private Transport t;




    /**
     * TODO# Implement port attribution dirty harry style. Open and close 'till one works.
     * Handles server setup. Network connections and thread context startup.
     * Assumes only it and an object store exist on the network and updates from there.
     */
    protected Server() {
        known = new ArrayList<>();
        known.add(new Address("127.0.0.1",10001));
        me = new Address("127.0.0.1", 10001);
        t = new NettyTransport();
        tc = new SingleThreadContext("srv-%d", new Serializer());
        dom = new DistObjManager(known, me);
    }

    protected abstract void execute();
}
