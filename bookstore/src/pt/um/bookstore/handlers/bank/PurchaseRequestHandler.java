package pt.um.bookstore.handlers.bank;

import pt.um.bookstore.bank.Bank;
import pt.um.bookstore.messaging.bank.PurchaseReply;
import pt.um.bookstore.messaging.bank.PurchaseRequest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * TODO including 2PC steps for registration.
 */
public class PurchaseRequestHandler
        implements Function<PurchaseRequest, CompletableFuture<PurchaseReply>>
{
    private Bank b;

    public PurchaseRequestHandler(Bank b)
    {
        this.b = b;
    }

    @Override
    public CompletableFuture<PurchaseReply> apply(PurchaseRequest rq)
    {
        b.registerPayment(rq.getClientID(), rq.getPayment());
        return CompletableFuture.completedFuture(new PurchaseReply(true));
    }
}
