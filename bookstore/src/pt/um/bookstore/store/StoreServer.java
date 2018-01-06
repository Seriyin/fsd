package pt.um.bookstore.store;

import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import pt.um.bookstore.handlers.store.BuyRequestHandler;
import pt.um.bookstore.handlers.store.FindRequestHandler;
import pt.um.bookstore.messaging.store.*;
import pt.um.bookstore.util.DistObjManager;
import pt.um.bookstore.util.RemoteObjectStore;
import pt.um.bookstore.util.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * StoreServer class contains the main logic to boot up a process in an unknown
 * network topology. This server will serve a book pt.um.bookstore.store.
 */
public class StoreServer extends Server
{
    private Store store;
    private RemoteObjectStore objstore;
    private DistObjManager dom;
    private ThreadContext tc;

    /**
     * StoreServer constructor must supply a
     * bank name.
     * <p>
     * Should follow store_{name} convention but is
     * not required.
     * @param name The name of the store to registerPayment
     */
    protected StoreServer(String name) {
        super(name);
        store = new StoreSkeleton(name, getDom());
    }

    /**
     * The main creates a StoreServer and executes it.
     * <p>
     * Expects store name as first argument,
     * otherwise defaults to store_default.
     * <p>
     * Please follow this naming convention: store_{name}
     * @param args shell arguments passed in.
     */
    public static void main(String args[]) {
        String name;
        if(args.length>0) {
            name = args[0];
        }
        else {
            name = "store_default";
        }
        StoreServer s  = new StoreServer(name);
        s.setup();
        s.execute();
    }

    /**
     * TODO implement execution.
     */
    @Override
    protected void execute() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        try {
            while(!bf.readLine().equals("quit"));
        }
        catch (IOException e) {
            //ups
        }
    }

    /**
     * TODO set pt.um.bookstore.handlers.
     * @param c The open {@link Transport#server()} connection.
     */
    @Override
    protected void handlers(Connection c) {
        c.handler(FindRequest.class, new FindRequestHandler(store));
        c.handler(BuyRequest.class, new BuyRequestHandler(store));
    }

    /**
     * TODO registerPayment serializables.
     */
    @Override
    protected void registerSerializables() {
        Serializer sr = getSerializer();
        sr.register(AddReply.class);
        sr.register(AddRequest.class);
        sr.register(BuyReply.class);
        sr.register(BuyRequest.class);
        sr.register(ClearRequest.class);
        sr.register(ClearReply.class);
        sr.register(FindRequest.class);
        sr.register(FindReply.class);
        sr.register(RemoveRequest.class);
        sr.register(RemoveReply.class);
    }

}
