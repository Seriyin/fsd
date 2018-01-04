package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * Heartbeat request is involved in discovery phase of the transaction.
 * <p>
 * Informs the coordinator of a resource's involvement as well as providing
 * an object to log in order to restore transaction in case of failure.
 * @param <T> A type of object to log that must be serializable.
 * @see CatalystSerializable
 */
public class HeartbeatRequest<T extends CatalystSerializable> extends ObjRequest
{
    private long xid;
    private T logged;

    public HeartbeatRequest(RemoteObj ro, long xid, T logged)
    {
        super(ro);
        this.xid = xid;
        this.logged = logged;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
        logged = serializer.readObject(buffer);
        xid = buffer.readLong();
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
        serializer.writeObject(logged, buffer);
        buffer.writeLong(xid);
    }
}
