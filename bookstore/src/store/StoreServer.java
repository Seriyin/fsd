package store;

import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.transport.Address;
import util.DistObjManager;
import util.ObjectStore;
import util.Server;

/**
 * StoreServer class contains the main logic to boot up a process in an unknown
 * network topology. This server will serve a book store.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.2
 */
public class StoreServer extends Server {
    private Store store;
    private ObjectStore objstore;
    private DistObjManager dom;
    private ThreadContext tc;
    private Address addresses[];

    /**
     * Assume safe defaults. A topology made of one object store process, a book store process,
     * a bank process and a client process.
     * <p>
     * Object store is number 0, Book store is number 1, Bank process is number 2, Client process is number 3.
     * @param args can contain a path to a JSON configuration file.
     */
    public static void main(String args[]) {
        StoreServer s  = new StoreServer();
        s.execute();
    }

    /**
     *
     */
    protected void execute() {
        // Register Requests and Replies
        // tc.serializer().register(StoreSearchRep.class);

        tc.execute(() -> {
/*
        cl.handler(Request.class, (j,m)-> {
                // message handler
            });
*/
        });
    }

}
