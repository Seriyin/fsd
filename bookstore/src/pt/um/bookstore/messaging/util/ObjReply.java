package pt.um.bookstore.messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.util.RemoteObj;

import java.util.Optional;


/**
 * ObjReply contains the remote reference of the object needed.
 */
public abstract class ObjReply implements Reply
{
    private Optional<RemoteObj> ro;

    ObjReply(Optional<RemoteObj> ro) {
        this.ro = ro;
    }

    public Optional<RemoteObj> getRemoteObj() {
        return ro;
    }


    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        if(buffer.readByte()==1)
        {
            ro = Optional.of(serializer.readObject(buffer));
        }
        else {
            ro = Optional.empty();
        }
    }


    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        if(ro.isPresent()) {
            buffer.writeByte(1);
            serializer.writeObject(ro, buffer);
        }
        else {
            buffer.writeByte(0);
        }
    }
}
