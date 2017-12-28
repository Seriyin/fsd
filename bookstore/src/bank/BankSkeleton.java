package bank;

import pt.haslab.ekit.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Bank Skeleton implements actual bank logic against a transactional log.
 *
 * @author André Diogo
 * @version 1.0, 27-12-2017
 *
 */
public class BankSkeleton implements Bank {
    private Map<Long,List<Payment>> mb;
    private Log payments;

    public BankSkeleton() {
        mb = new HashMap<>();
        payments = new Log("payments");
        registerHandlers();
    }


    /**
     * Register handlers for restructuring payment map from log.
     *
     * Must act in accordance with 2PC principles.
     */
    private void registerHandlers() {
        payments.handler()
                .handler()
                .handler();
        //Await coherence
        payments.open().join();
    }

    /**
     * Registering a payment is included in a distributed transaction
     * involving a purchase in a store.
     *
     * Is guaranteed to fail fast or succeed.
     * @param cid The client identifier.
     * @param p A payment that contains the items involved as well has the charge.
     * @return Success or failure.
     */
    @Override
    public boolean register(long cid, Payment p) {
        return false;
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
            result = mb.get(cid);
        }
        else {
            result = new ArrayList<>();
        }
        return result;
    }

}
