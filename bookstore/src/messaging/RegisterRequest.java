package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * RegisterRequest encapsulates a request to send to a naming service.
 * <p>
 * Must contain a RemoteObj of the main object to export globally, as well
 * as a String identifying the name under which it is retrievable.
 */
public class RegisterRequest extends ObjRequest {
    private String regName;

    public RegisterRequest(RemoteObj ro, String name) {
        super(ro);
        regName = name;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        getRemoteObj().writeObject(buffer,serializer);
        serializer.writeObject(regName);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        setRemoteObj(serializer.readObject(buffer));
        regName = buffer.readString();
    }
}
