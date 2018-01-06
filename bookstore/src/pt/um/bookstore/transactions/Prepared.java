package pt.um.bookstore.transactions;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.util.RemoteObj;

/**
 * A prepared marker from a given resource.
 * <p>
 * Checked against all known resources.
 * <p>
 * Starts up a commit on convergence.
 */
public class Prepared implements CatalystSerializable
{
    private long xid;
    private RemoteObj ro;

    public Prepared(long xid, RemoteObj ro)
    {
        this.xid = xid;
        this.ro = ro;
    }

    public long getXID()
    {
        return xid;
    }

    public RemoteObj getRef()
    {
        return ro;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        buffer.writeLong(xid);
        serializer.writeObject(ro,buffer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        xid = buffer.readLong();
        ro = serializer.readObject(buffer);
    }
}
