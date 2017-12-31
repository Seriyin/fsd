package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;


/**
 * ObjReply contains the remote reference of the object needed.
 */
public abstract class ObjReply implements CatalystSerializable {
    private RemoteObj ro;

    ObjReply(RemoteObj ro) {
        this.ro = ro;
    }

    RemoteObj getRemoteObj() {
        return ro;
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
