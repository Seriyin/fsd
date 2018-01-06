package pt.um.bookstore.transactions;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

public class Commit implements CatalystSerializable
{
    private long txid;

    public long getTxID()
    {
        return txid;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        buffer.writeLong(txid);
    }


    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        txid = buffer.readLong();
    }
}
