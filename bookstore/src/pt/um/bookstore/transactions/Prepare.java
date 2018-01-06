package pt.um.bookstore.transactions;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 *  A prepare marker for a given transaction.
 *  <p>
 *  Must follow heartbeats of all involved resources.
 */
public class Prepare implements CatalystSerializable
{
    private long xid;

    public Prepare(long xid)
    {
        this.xid = xid;
    }

    public long getXID()
    {
        return xid;
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        buffer.writeLong(xid);
    }


    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        xid = buffer.readLong();
    }
}
