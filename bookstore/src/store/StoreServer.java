package store;

import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import util.DistObjManager;
import util.RemoteObjectStore;
import util.Server;

/**
 * StoreServer class contains the main logic to boot up a process in an unknown
 * network topology. This server will serve a book store.
 */
public class StoreServer extends Server {
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
     * @param name The name of the store to register
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
        tc.execute(() -> {
/*
        cl.handler(Request.class, (j,m)-> {
                // message handler
            });
*/
        });
    }

    /**
     * TODO set handlers.
     * @param c The open {@link Transport#server()} connection.
     */
    @Override
    protected void handlers(Connection c) {

    }

    /**
     * TODO register serializables.
     */
    @Override
    protected void registerSerializables() {

    }

}
