package pt.um.bookstore.handlers.transactions;

import pt.um.bookstore.handlers.util.LogHandler;
import pt.um.bookstore.transactions.HeartBeat;
import pt.um.bookstore.transactions.TransactionsManager;

/**
 * Handler for informing transaction manager of heartbeat.
 * <p>
 * Type erasure does not affect contract for HeartBeat.
 */
public class HeartBeatHandler implements LogHandler<HeartBeat>
{
    private TransactionsManager coord;

    public HeartBeatHandler(TransactionsManager coord)
    {
        this.coord = coord;
    }

    @Override
    public void accept(Integer integer, HeartBeat rq)
    {
        coord.heartbeat(rq.getXID(),rq.getRef(),rq.getLogged());
    }
}
