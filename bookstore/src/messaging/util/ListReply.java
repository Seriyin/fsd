package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

import java.util.List;

/**
 * A list reply collapses a use case of returning a list in a reply.
 * @param <T> The type of list element. Must be serializable.
 * @see CatalystSerializable
 */
public abstract class ListReply<T extends CatalystSerializable>
        implements CatalystSerializable
{
    private List<T> l;

    public ListReply(List<T> l)
    {
        this.l = l;
    }

    public List<T> getList()
    {
        return l;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        l = serializer.readObject(buffer);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        serializer.writeObject(l,buffer);
    }
}
