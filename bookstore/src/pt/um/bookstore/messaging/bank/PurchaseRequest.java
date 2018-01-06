package pt.um.bookstore.messaging.bank;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.bank.Item;
import pt.um.bookstore.bank.Payment;
import pt.um.bookstore.messaging.util.ObjRequest;
import pt.um.bookstore.store.Book;
import pt.um.bookstore.util.RemoteObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Purchase Request takes the list of books from the cart and
 * client id from the store to synchronize in the bank.
 *
 * Constructs the payment from the cart list.
 */
public class PurchaseRequest extends ObjRequest
{
    private Payment p;
    private long cid;

    public PurchaseRequest(RemoteObj ro, long cid, List<pt.um.bookstore.store.Item<Book>> bl) {
        super(ro);
        this.p = new Payment(bl.stream()
                               .collect(ArrayList::new,
                                       (li,b) -> li.add(new Item(b.getItem().getTitle(),
                                                                 b.getItem().getPrice(),
                                                                 b.getQuantity())),
                                        ArrayList::addAll));
        this.cid = cid;
    }

    public PurchaseRequest(RemoteObj ro, long cid, Payment p)
    {
        super(ro);
        this.cid = cid;
        this.p = p;
    }

    public Payment getPayment() {
        return p;
    }

    public long getClientID() {
        return cid;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
        serializer.writeObject(p, buffer);
        buffer.writeLong(cid);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        p = serializer.readObject(buffer);
        cid = buffer.readLong();
    }

}
