package messaging.util;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Reply which contains a boolean indicating operation success.
 */
public abstract class SReply implements CatalystSerializable {
    private boolean hasSucceeded;

    protected SReply(boolean hasSucceeded){
        this.hasSucceeded = hasSucceeded;
    }

    public boolean hasSucceeded() {
        return hasSucceeded;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        buffer.writeBoolean(hasSucceeded);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        hasSucceeded = buffer.readBoolean();
    }
}
