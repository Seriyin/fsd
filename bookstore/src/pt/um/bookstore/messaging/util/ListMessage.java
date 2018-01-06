package pt.um.bookstore.messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

import java.util.List;

/**
 * A list message collapses a use case of returning a list in a message(either Request or Reply).
 * <p>
 * List message is neither Request nor Reply as it can be either depending on the underlying
 * message type.
 * @param <T> The type of list element. Must be serializable.
 * @see CatalystSerializable
 */
public abstract class ListMessage<T extends CatalystSerializable>
        implements CatalystSerializable
{
    private List<T> l;

    public ListMessage(List<T> l)
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
