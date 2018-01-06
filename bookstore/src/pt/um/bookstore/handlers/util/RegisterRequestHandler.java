package pt.um.bookstore.handlers.util;

import pt.um.bookstore.messaging.util.InsertRemoteObjReply;
import pt.um.bookstore.messaging.util.InsertRemoteObjRequest;
import pt.um.bookstore.util.RemoteObj;
import pt.um.bookstore.util.RemoteObjectStore;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * TODO logging.
 * Register request handler always returns a completedFuture for now.
 */
public class RegisterRequestHandler
        implements Function<InsertRemoteObjRequest, CompletableFuture<InsertRemoteObjReply>>
{
    private RemoteObjectStore store;

    public RegisterRequestHandler(RemoteObjectStore store)
    {
        this.store = store;
    }

    @Override
    public CompletableFuture<InsertRemoteObjReply> apply(InsertRemoteObjRequest rq)
    {
        String name = rq.getName();
        RemoteObj ro = rq.getRemoteObj();
        store.insertObject(name, ro);
        return CompletableFuture.completedFuture(new InsertRemoteObjReply(true));
    }
}

