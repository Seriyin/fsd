package pt.um.bookstore.store;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The accounts class holds all client accounts and manages
 * registered but unapproved payments.
 */
public class Accounts
{
    private Map<Long,Account> ma;
    private Map<Long,Purchase> pending;
    private AtomicLong l;


    public Accounts()
    {
        ma = new HashMap<>();
        pending = new HashMap<>();
        l = new AtomicLong(0);
    }

    public Map<Long, Account> getAccounts()
    {
        return ma;
    }

    public Map<Long, Purchase> getPending()
    {
        return pending;
    }

    /**
     * Add a purchase into pending status.
     * @param txid the client id.
     * @param p the purchase that awaits approval.
     */
    public void addPurchase(long txid, Purchase p)
    {
        pending.put(txid,p);
    }

    /**
     * @return Returns a new unique tag for client id.
     */
    public long newTag()
    {
        return l.incrementAndGet();
    }

    /**
     * Commit a pending purchase.
     * @param txID the transaction id.
     */
    public void commit(long txID)
    {
        Purchase p = pending.get(txID);
        ma.get(p.getCID()).addPurchase(p);
    }

    /**
     * Abort a pending purchase.
     * @param txID the transaction id.
     */
    public void abort(long txID)
    {
        Purchase p = pending.remove(txID);
    }
}
