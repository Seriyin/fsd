package bank;

import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import util.RemoteObj;
import util.Stub;

import java.util.List;

public class BankStub extends Stub implements Bank {
    private List<Payment> paymentCache;


    public BankStub(RemoteObj b, Connection c)
    {
        super(b,c);
        paymentCache = null;
    }

    @Override
    public boolean register(long cid, Payment p) {
        return false;
    }

    @Override
    public List<Payment> consult(long cid) {
        return null;
    }

}
