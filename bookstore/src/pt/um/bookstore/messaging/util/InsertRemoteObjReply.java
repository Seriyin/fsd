package pt.um.bookstore.messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Another success of fail response to an insert into a remote object pt.um.bookstore.store.
 */
public class InsertRemoteObjReply extends SReply
{
    public InsertRemoteObjReply(boolean hasSucceeded)
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
