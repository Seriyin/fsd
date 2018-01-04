package bank;

import messaging.util.InsertRemoteObjRequest;
import pt.haslab.ekit.Log;
import util.DistObjManager;
import util.Skeleton;

import java.util.*;

/**
 * Bank Skeleton implements actual bank logic against a transactional log.
 *
 */
public class BankSkeleton extends Skeleton implements Bank {
    private DistObjManager dom;
    private Map<Long,Account> mb;
    private Log payments;

    /**
     * Creates a bank skeleton that will be registered
     * with the default naming service.
     * @param name The bank's name.
     * @param dom The DistObjManager to use for
     *            remote reference management.
     */
    public BankSkeleton(String name, DistObjManager dom) {
        mb = new HashMap<>();
        payments = new Log(name);
        this.dom = dom;
        setRef(dom.exportRef(this).orElse(null));
        registerHandlers();
        dom.sendRegisterRequest(new InsertRemoteObjRequest(getRef(), name));
    }


    /**
     * #TODO build handlers for 2PC.
     * Register messaging for restructuring payment map from log.
     *
     * Must act in accordance with 2PC principles.
     */
    private void registerHandlers() {
        /*payments.handler()
                .handler()
                .handler();
        //Await coherence
        payments.open().join();*/
    }


    @Override
    public boolean registerClient(String name)
    {
        boolean hasSucceeded = false;
        long cid = Objects.hash(name,System.nanoTime());
        if(!mb.containsKey(cid))
        {
            mb.put(cid,new Account(name, cid));
            hasSucceeded = true;
        }
        return hasSucceeded;
    }

    /**
     * #TODO implement registering a payment as part of a distributed transaction.
     * Registering a payment is included in a distributed transaction
     * involving a purchase in a store.
     *
     * Is guaranteed to fail fast or succeed.
     * @param cid The client identifier.
     * @param p A payment that contains the items involved as well has the charge.
     * @return Success or failure.
     */
    @Override
    public boolean registerPayment(long cid, Payment p) {
        boolean hasSucceeded = false;
        if(mb.containsKey(cid))
        {
            hasSucceeded = mb.get(cid)
                             .getPaymentHistory()
                             .add(p);
        }
        return hasSucceeded;
    }


    /**
     * Consult latest payments.
     * @param cid The client identifier.
     * @return the list of all payments made by that client (may be empty but never null).
     */
    @Override
    public List<Payment> consult(long cid) {
        List<Payment> result;
        if(mb.containsKey(cid)) {
            result = mb.get(cid).getPaymentHistory();
        }
        else {
            result = new ArrayList<>();
        }
        return result;
    }

}
