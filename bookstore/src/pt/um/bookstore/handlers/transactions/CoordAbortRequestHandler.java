package pt.um.bookstore.handlers.transactions;

import pt.um.bookstore.handlers.util.ConsumingHandler;
import pt.um.bookstore.messaging.transactions.AbortRequest;
import pt.um.bookstore.transactions.TransactionsManager;

public class CoordAbortRequestHandler implements ConsumingHandler<AbortRequest>
{
    private TransactionsManager tm;

    public CoordAbortRequestHandler(TransactionsManager tm)
    {
        this.tm = tm;
    }


    @Override
    public void accept(AbortRequest rq)
    {
        tm.abort(rq.getID());
    }
}
