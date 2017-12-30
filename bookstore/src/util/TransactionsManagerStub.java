package util;

import io.atomix.catalyst.transport.Transport;

public class TransactionsManagerStub extends Stub implements TransactionsManager {


    public TransactionsManagerStub(RemoteObj b, Transport t) {
        super(b,t);
    }
}
