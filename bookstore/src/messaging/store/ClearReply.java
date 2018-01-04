package messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import messaging.util.SReply;

/**
 * Clear reply is another success or fail reply.
 */
public class ClearReply extends SReply
{
    ClearReply(boolean hasSucceeded)
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
