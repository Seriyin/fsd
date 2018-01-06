package pt.um.bookstore.messaging.bank;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.ObjRequest;
import pt.um.bookstore.util.RemoteObj;

/**
 * Request to register a client in a bank.
 */
public class ClientRequest extends ObjRequest
{
    private String name;

    public ClientRequest(RemoteObj ro, String name)
    {
        super(ro);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
        buffer.writeString(name);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
        name = buffer.readString();
    }
}
