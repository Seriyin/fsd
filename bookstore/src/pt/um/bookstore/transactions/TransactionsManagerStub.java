package pt.um.bookstore.transactions;

import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.transport.Connection;
import pt.um.bookstore.messaging.transactions.*;
import pt.um.bookstore.util.RemoteObj;
import pt.um.bookstore.util.Stub;

public class TransactionsManagerStub extends Stub implements TransactionsManager {


    public TransactionsManagerStub(RemoteObj b, Connection c) {
        super(b,c);
    }

    @Override
    public long begin() {
        return getConnection().<BeginTransactionRequest, BeginTransactionReply>sendAndReceive(new BeginTransactionRequest(getRef()))
                              .join()
                              .getXID();
    }

    @Override
    public <T extends CatalystSerializable> void heartbeat(long xid, RemoteObj ro, T logged) {
        getConnection().send(new HeartbeatRequest<>(ro, xid, logged));
    }

    @Override
    public void prepare(long xid)
    {
        getConnection().send(new PrepareRequest(xid));
    }

    @Override
    public void prepared(long xid, RemoteObj ro) {
        getConnection().send(new PreparedRequest(xid, ro));
    }


    @Override
    public void abort(long xid) {
        getConnection().send(new AbortRequest(xid));
    }
}
