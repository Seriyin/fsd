package bank;

import java.util.List;

/**
 * Bank describes operations allowed over payments.
 * <p>
 * Allows payment registration and consultation.
 */
public interface Bank {

    /**
     * Register a payment.
     * @param cid The client identifier.
     * @param p A payment that contains the items involved as well has the charge.
     * @return whether the registration succeeded.
     */
    boolean register(long cid, Payment p);

    /**
     * Consult all payments to date.
     * @param cid The client identifier.
     * @return a list with all the payments to date.
     */
    List<Payment> consult(long cid);
}