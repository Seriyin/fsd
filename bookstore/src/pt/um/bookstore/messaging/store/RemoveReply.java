package pt.um.bookstore.messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.SReply;

/**
 * Simple success reply to remove from cart operation.
 */
public class RemoveReply extends SReply
{

    RemoveReply(boolean hasSucceeded) {
        super(hasSucceeded);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
    }
}
