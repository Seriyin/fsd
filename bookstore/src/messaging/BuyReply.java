package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;

/**
 * BuyReply holds the purchase transaction result.
 */
public class BuyReply extends SReply {
    public BuyReply(boolean hasSucceeded) {
        super(hasSucceeded);
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer,serializer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer,serializer);
    }
}
