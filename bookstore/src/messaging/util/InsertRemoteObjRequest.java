package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * Insert remote reference into a remote object store.
 */
public class InsertRemoteObjRequest extends ObjRequest
{
    private String s;

    public InsertRemoteObjRequest(RemoteObj ro, String name)
    {
        super(ro);
        this.s = s;
    }


    public String getName()
    {
        return s;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
        buffer.writeString(s);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
        s = buffer.readString();
    }


}
