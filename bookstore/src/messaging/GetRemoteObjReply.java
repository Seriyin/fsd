package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

import java.util.Optional;

/**
 * Remote Object reply from a naming service.
 */
public class GetRemoteObjReply extends ObjReply {
    GetRemoteObjReply(Optional<RemoteObj> ro) {
        super(ro);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
    }
}
