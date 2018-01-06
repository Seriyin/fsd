package pt.um.bookstore.messaging.transactions;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.TagRequest;

/**
 * Request by a resource in a transaction to abort the transaction.
 * <p>
 * Also used by transaction manager to inform resources of transaction abort.
 */
public class AbortRequest extends TagRequest
{
    public AbortRequest(long xid)
    {
        super(xid);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
    }
}
