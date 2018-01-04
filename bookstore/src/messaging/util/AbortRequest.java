package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Request by a resource in a transaction to abort the transaction.
 * <p>
 * Also used by transaction manager to inform resources of transaction abort.
 */
public class AbortRequest implements CatalystSerializable
{
    private long xid;

    public AbortRequest(long xid)
    {
        this.xid = xid;
    }

    public long getXID()
    {
        return xid;
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        buffer.writeLong(xid);
    }


    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        xid = buffer.readLong();
    }
}
