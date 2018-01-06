package pt.um.bookstore.handlers.store;

import pt.um.bookstore.handlers.util.WithReplyHandler;
import pt.um.bookstore.messaging.store.BuyReply;
import pt.um.bookstore.messaging.store.BuyRequest;
import pt.um.bookstore.store.StoreSkeleton;

import java.util.concurrent.CompletableFuture;

/**
 * Forwards a buy request to a store from a client, given a cart.
 * <p>
 * Will trigger a distributed transaction.
 */
public class BuyRequestHandler
        implements WithReplyHandler<BuyRequest,BuyReply>
{
    private StoreSkeleton store;

    public BuyRequestHandler(StoreSkeleton store)
    {
        this.store = store;
    }


    @Override
    public CompletableFuture<BuyReply> apply(BuyRequest rq)
    {
        store.buy(rq.getXID(),rq.getList(),rq.getCID());
        return null;
    }
}
