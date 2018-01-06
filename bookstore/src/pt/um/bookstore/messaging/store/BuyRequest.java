package pt.um.bookstore.messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.ListMessage;
import pt.um.bookstore.messaging.util.Request;
import pt.um.bookstore.store.Book;
import pt.um.bookstore.store.Item;

import java.util.List;

/**
 * BuyRequest is a request to a store to buy books in a cart.
 */
public class BuyRequest extends ListMessage<Item<Book>> implements Request
{
    private long cid;
    private long xid;

    public BuyRequest(long xid, List<Item<Book>> lb, long cid)
    {
        super(lb);
        this.xid = xid;
        this.cid = cid;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
        buffer.writeLong(cid);
        buffer.writeLong(xid);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        cid = buffer.readLong();
        xid = buffer.readLong();
    }

    public long getCID()
    {
        return cid;
    }

    public long getXID()
    {
        return xid;
    }
}
