package messaging;

import bank.Item;
import bank.Payment;
import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import store.Book;
import util.RemoteObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Purchase Request takes the payment to synchronize in the bank.
 *
 * Constructs the payment from the cart list.
 */
public class PurchaseRequest extends ObjRequest {
    private Payment p;
    private long cid;

    public PurchaseRequest(RemoteObj ro, List<Book> bl) {
        super(ro);
        this.p = new Payment(bl.stream()
                               .collect(ArrayList::new,
                                       (li,b) -> li.add(new Item(b.getTitle(),b.getPrice())),
                                       (li1,li2) -> li1.addAll(li2)));
        this.cid = cid;
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
        serializer.writeObject(p);
        buffer.writeLong(cid);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        p = serializer.readObject(buffer);
        cid = buffer.readLong();
    }

}
