package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * Remote object request to a naming service.
 */
public class GetRemoteObjRequest extends ObjRequest
{
    private String name;
    private long tag;

    public GetRemoteObjRequest(RemoteObj ro, String name, long tag) {
        super(ro);
        this.name = name;
        this.tag = tag;
    }

    public GetRemoteObjRequest(RemoteObj ro, String name) {
        super(ro);
        this.name = name;
        this.tag = -1;
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
