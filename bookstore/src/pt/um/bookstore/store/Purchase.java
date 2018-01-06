package pt.um.bookstore.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

import java.util.List;

/**
 * A purchase involves some books and the quantity ordered.
 */
public class Purchase implements CatalystSerializable
{
    private long cid;
    private List<Item<Book>> purchase;
    private double totalCharge;

    public Purchase(long cid, List<Item<Book>> purchase)
    {
        this.cid = cid;
        this.purchase = purchase;
        this.totalCharge = purchase.stream()
                                   .mapToDouble(b -> b.getQuantity() * b.getItem().getPrice())
                                   .sum();
    }

    public long getCID()
    {
        return cid;
    }

    public List<Item<Book>> getPurchase()
    {
        return purchase;
    }

    public double getTotalCharge()
    {
        return totalCharge;
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        buffer.writeLong(cid);
        serializer.writeObject(purchase,buffer);
        buffer.writeDouble(totalCharge);
    }


    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        cid = buffer.readLong();
        purchase = serializer.readObject(buffer);
        totalCharge = buffer.readDouble();
    }
}
