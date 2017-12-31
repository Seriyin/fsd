package messaging;

import bank.Payment;
import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Replies with the list of payments the client has made in the bank.
 * <p>
 * Is immutable and does not mutate the underlying list.
 */
public class ConsultReply implements CatalystSerializable {
    private List<Payment> lp;

    /**
     * Does not matter if the list has been cloned or not.
     * <p>
     * Will be serialized.
     * @param lp The list of payments consulted.
     */
    public ConsultReply(final List<Payment> lp) {
        this.lp = lp;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        buffer.writeInt(lp.size());
        for(Payment p : lp) {
            buffer.writeByte(1);
            serializer.writeObject(p);
        }
        buffer.writeByte(0);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        lp = new ArrayList<>(buffer.readInt());
        while(buffer.readByte()!=0) {
            lp.add(serializer.readObject(buffer));
        }
    }
}
