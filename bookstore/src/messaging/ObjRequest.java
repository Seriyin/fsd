package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;


/**
 * ObjRequest is an abstract class taking a gettable RemoteObj that is to
 * be included in the specific request.
 */
public abstract class ObjRequest implements CatalystSerializable
{
    private RemoteObj ro;

    ObjRequest(RemoteObj ro) {
        this.ro = ro;
    }

    public RemoteObj getRemoteObj() {
        return ro;
    }

    void setRemoteObj(RemoteObj ro) {
        this.ro = ro;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        ro = serializer.readObject(buffer);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        serializer.writeObject(ro);
    }
}
