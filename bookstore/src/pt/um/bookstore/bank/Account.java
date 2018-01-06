package pt.um.bookstore.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Account represent a bank account with client name, id and payment history.
 */
public class Account {
    private String name;
    private long cid;
    private List<Payment> lp;

    /**
     * Account made by reconstructing from a persistant store.
     * @param name The client's name.
     * @param cid The client's id.
     * @param lp The payment history of the client.
     */
    public Account(String name, long cid, List<Payment> lp)
    {
        this.name = name;
        this.cid = cid;
        this.lp = lp;
    }

    /**
     * Account made from scratch.
     * @param name The client's name.
     * @param cid The client's id.
     */
    public Account(String name, long cid)
    {
        this.name = name;
        this.cid = cid;
        lp = new ArrayList<>();
    }

    public String getName()
    {
        return name;
    }

    public long getCID()
    {
        return cid;
    }

    public List<Payment> getPaymentHistory()
    {
        return lp;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Account account = (Account) o;
        return cid == account.cid &&
               Objects.equals(getName(), account.getName()) &&
               Objects.equals(getPaymentHistory(), account.getPaymentHistory());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getName(), cid, getPaymentHistory());
    }

    @Override
    public String toString()
    {
        return "Account {" +
               System.lineSeparator() +
               "\tname='" + name + "\'" +
               System.lineSeparator() +
               ", Client id=" + cid +
               System.lineSeparator() +
               ", Payments=" + lp +
               '}';
    }
}
