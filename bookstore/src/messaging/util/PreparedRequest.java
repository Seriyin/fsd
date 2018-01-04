package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * Request sent by a resource indicating it's prepared to commit active transaction.
 */
public class PreparedRequest extends ObjRequest
{
    private long xid;

    public PreparedRequest(long xid, RemoteObj ro)
    {
        super(ro);
        this.xid = xid;
    }

    public long getXID()
    {
        return xid;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
        buffer.writeLong(xid);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
        xid = buffer.readLong();
    }
}
