package pt.um.bookstore.messaging.bank;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.SReply;

/**
 * Client reply is yet another success of fail reply.
 */
public class ClientReply extends SReply
{
    public ClientReply(boolean hasSucceeded)
    {
        super(hasSucceeded);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
    }
}
