package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Reply with transaction id.
 */
public class BeginTransactionReply implements CatalystSerializable
{
    private long xid;

    BeginTransactionReply(long xid)
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
