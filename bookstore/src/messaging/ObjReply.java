package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

import java.util.Optional;


/**
 * ObjReply contains the remote reference of the object needed.
 */
public abstract class ObjReply implements CatalystSerializable {
    private Optional<RemoteObj> ro;

    ObjReply(Optional<RemoteObj> ro) {
        this.ro = ro;
    }

    public Optional<RemoteObj> getRemoteObj() {
        return ro;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        if(buffer.readByte()==1)
        {
            ro = Optional.of(serializer.readObject(buffer));
        }
        else {
            ro = Optional.empty();
        }
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        if(ro.isPresent()) {
            buffer.writeByte(1);
            serializer.writeObject(ro);
        }
        else {
            buffer.writeByte(0);
        }
    }
}
