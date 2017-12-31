package messaging;

import bank.Payment;
import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * Purchase Request takes the payment to synchronize in the bank.
 */
public class PurchaseRequest extends ObjRequest {
    private Payment p;
    private long cid;

    public PurchaseRequest(RemoteObj ro, Payment p) {
        super(ro);
        this.p = p;
        this.cid = cid;
    }

    public Payment getPayment() {
        return p;
    }

    public long getClientID() {
        return cid;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
        serializer.writeObject(p);
        buffer.writeLong(cid);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        p = serializer.readObject(buffer);
        cid = buffer.readLong();
    }

}
