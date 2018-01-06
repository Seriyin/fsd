package pt.um.bookstore.messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.ObjRequest;
import pt.um.bookstore.util.RemoteObj;

/**
 * Clear request is a request to clear a remote cart.
 */
public class ClearRequest extends ObjRequest
{
    public ClearRequest(RemoteObj ro)
    {
        super(ro);
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
