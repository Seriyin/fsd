package pt.um.bookstore.messaging.transactions;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.TagReply;

/**
 * Reply with transaction id.
 */
public class BeginTransactionReply extends TagReply
{
    private long xid;

    BeginTransactionReply(long xid)
    {
        super(xid);
    }

    public long getXID()
    {
        return xid;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer,serializer);
    }
}
