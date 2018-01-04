package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Prepare request sent by client to indicate start of first phase of two-phase commit.
 */
public class PrepareRequest implements CatalystSerializable
{
    private long xid;

    public PrepareRequest(long xid)
    {
        this.xid = xid;
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
