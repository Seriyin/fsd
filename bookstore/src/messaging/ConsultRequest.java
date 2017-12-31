package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;


/**
 * ConsultRequest needs only the bank remote reference and a client id.
 */
public class ConsultRequest extends ObjRequest {
    private long cid;

    public ConsultRequest(RemoteObj ro, long cid) {
        super(ro);
        this.cid = cid;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        cid = buffer.readLong();
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
        buffer.writeLong(cid);
    }

    public long getClientID() {
        return cid;
    }
}
