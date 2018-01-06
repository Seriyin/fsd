package pt.um.bookstore.handlers.bank;

import pt.um.bookstore.bank.Bank;
import pt.um.bookstore.handlers.util.WithReplyHandler;
import pt.um.bookstore.messaging.bank.ClientReply;
import pt.um.bookstore.messaging.bank.ClientRequest;

import java.util.concurrent.CompletableFuture;

/**
 * ClientRequest registers a new client into the bank.
 */
public class ClientRequestHandler
        implements WithReplyHandler<ClientRequest,ClientReply>
{
    private Bank b;

    public ClientRequestHandler(Bank b)
    {
        this.b = b;
    }

    @Override
    public CompletableFuture<ClientReply> apply(ClientRequest rq)
    {
        b.registerClient(rq.getName());
        return CompletableFuture.completedFuture(new ClientReply(true));
    }
}