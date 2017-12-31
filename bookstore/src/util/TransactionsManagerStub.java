package util;

import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;

public class TransactionsManagerStub extends Stub implements TransactionsManager {


    public TransactionsManagerStub(RemoteObj b, Connection c) {
        super(b,c);
    }
}
