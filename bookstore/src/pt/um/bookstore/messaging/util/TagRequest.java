package pt.um.bookstore.messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Abstract request with a tag.
 */
public abstract class TagRequest implements Request
{
    private long l;

    public TagRequest(long l)
    {
        this.l = l;
    }

    public long getID()
    {
        return l;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        l = buffer.readLong();
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        buffer.writeLong(l);
    }
}
