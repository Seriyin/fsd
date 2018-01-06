package pt.um.bookstore.handlers.util;

import pt.um.bookstore.messaging.util.GetRemoteObjReply;
import pt.um.bookstore.messaging.util.GetRemoteObjRequest;
import pt.um.bookstore.util.RemoteObj;
import pt.um.bookstore.util.RemoteObjectStore;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * Handles a request to get a remote reference.
 */
public class GetRequestHandler
        implements Function<GetRemoteObjRequest, CompletableFuture<GetRemoteObjReply>>
{
    private RemoteObjectStore store;

    public GetRequestHandler(RemoteObjectStore store)
    {
        this.store = store;
    }

    @Override
    public CompletableFuture<GetRemoteObjReply> apply(GetRemoteObjRequest rq)
    {
        Optional<RemoteObj> ro;
        if(rq.getTag()!=-1)
        {
            ro = store.getObject(rq.getName(),rq.getTag());
        }
        else {
            ro = store.getObject(rq.getName());
        }
        return CompletableFuture.completedFuture(new GetRemoteObjReply(ro));
    }
}
