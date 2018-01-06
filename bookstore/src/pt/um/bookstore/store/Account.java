package pt.um.bookstore.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Account of a client in a store specifies his purchases, his ids, and
 * his bank as well as his bank id.
 */
public class Account implements CatalystSerializable
{
    private String user;
    private String bank;
    private long cid;
    private long bankcid;
    private List<Purchase> purchases;

    public Account(String user, String bank, long cid, long bankcid)
    {
        this.user = user;
        this.bank = bank;
        this.cid = cid;
        this.bankcid = bankcid;
        this.purchases = new ArrayList<>();
    }

    public List<Purchase> getPurchases()
    {
        return purchases;
    }

    public String getUser()
    {
        return user;
    }

    public String getBank()
    {
        return bank;
    }

    public long getCID()
    {
        return cid;
    }

    public long getBankCID()
    {
        return bankcid;
    }

    public boolean addPurchase(Purchase purchase)
    {
        return purchases.add(purchase);
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        serializer.writeObject(user);
        serializer.writeObject(bank);
        buffer.writeLong(cid);
        buffer.writeLong(bankcid);
        serializer.writeObject(purchases,buffer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        user = serializer.readObject(buffer);
        bank = serializer.readObject(buffer);
        cid = buffer.readLong();
        bankcid = buffer.readLong();
        purchases = serializer.readObject(buffer);
    }
}
