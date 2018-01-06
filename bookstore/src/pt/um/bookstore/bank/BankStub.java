package pt.um.bookstore.bank;

import io.atomix.catalyst.transport.Connection;
import pt.um.bookstore.messaging.bank.*;
import pt.um.bookstore.util.RemoteObj;
import pt.um.bookstore.util.Stub;

import java.util.List;


/**
 * #TODO meaningful caching.
 * <p>
 * Sends requests to the actual bank.
 * <p>
 * Acts synchronously to keep interface standard and
 * make distributed behaviour transparent.
 */
public class BankStub extends Stub implements Bank {
    /**
     * Constructor that takes regular stub parameters.
     * @param b remote reference to bank resource.
     * @param c client connection to bank resource
     */
    public BankStub(RemoteObj b, Connection c)
    {
        super(b,c);
    }

    @Override
    public boolean registerClient(String name)
    {
        return getConnection().<ClientRequest, ClientReply>
                                       sendAndReceive(new ClientRequest(getRef(), name))
                              .join()
                              .hasSucceeded();
    }

    @Override
    public boolean registerPayment(long cid, Payment p)
    {
        return getConnection()
                       .<PurchaseRequest, PurchaseReply>
                                sendAndReceive(new PurchaseRequest(getRef(), cid, p))
                       .join()
                       .hasSucceeded();
    }

    @Override
    public List<Payment> consult(long cid) {
        return getConnection().<ConsultRequest, ConsultReply>
                                       sendAndReceive(new ConsultRequest(getRef(), cid))
                              .join()
                              .getPaymentHistory();
    }

}
