package pt.um.bookstore.handlers.bank;

import pt.um.bookstore.bank.Bank;
import pt.um.bookstore.bank.Payment;
import pt.um.bookstore.handlers.util.WithReplyHandler;
import pt.um.bookstore.messaging.bank.ConsultReply;
import pt.um.bookstore.messaging.bank.ConsultRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * ConsultRequest returns the payment list for the client requested.
 */
public class ConsultRequestHandler
        implements WithReplyHandler<ConsultRequest,ConsultReply>
{
    private Bank b;

    public ConsultRequestHandler(Bank b)
    {
        this.b = b;
    }

    @Override
    public CompletableFuture<ConsultReply> apply(ConsultRequest rq) {
        List<Payment> lp = b.consult(rq.getClientID());
        return CompletableFuture.completedFuture(new ConsultReply(lp));
    }
}