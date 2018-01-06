package pt.um.bookstore.transactions;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.util.RemoteObj;

/**
 * Heartbeat is a container for useful info about a resource.
 * @param <T> The type of the resource which must be serializable.
 *
 */
public class HeartBeat<T extends CatalystSerializable> implements CatalystSerializable
{
    private long xid;
    private RemoteObj ro;
    private T logged;

    public HeartBeat(long xid, RemoteObj ro, T logged)
    {
        this.xid = xid;
        this.ro = ro;
        this.logged = logged;
    }

    public long getXID()
    {
        return xid;
    }

    public RemoteObj getRef()
    {
        return ro;
    }

    public T getLogged()
    {
        return logged;
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        buffer.writeLong(xid);
        serializer.writeObject(ro, buffer);
        serializer.writeObject(logged);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        xid = buffer.readLong();
        ro = serializer.readObject(buffer);
        logged = serializer.readObject(buffer);
    }
}