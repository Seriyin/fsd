package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * Request to begin a transaction from host at the provided remote reference.
 */
public class BeginTransactionRequest extends ObjRequest
{
    public BeginTransactionRequest(RemoteObj ref)
    {
        super(ref);
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
