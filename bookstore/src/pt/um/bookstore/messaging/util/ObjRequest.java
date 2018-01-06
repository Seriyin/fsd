package pt.um.bookstore.messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.util.RemoteObj;


/**
 * ObjRequest is an abstract class taking a gettable RemoteObj that is to
 * be included in the specific request.
 */
public abstract class ObjRequest implements Request
{
    private RemoteObj ro;

    protected ObjRequest(RemoteObj ro) {
        this.ro = ro;
    }

    public RemoteObj getRemoteObj() {
        return ro;
    }

    protected void setRemoteObj(RemoteObj ro) {
        this.ro = ro;
    }


    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        ro = serializer.readObject(buffer);
    }


    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        serializer.writeObject(ro, buffer);
    }
}
