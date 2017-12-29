package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Register reply is a simple reply to a naming service registration,
 * with a success indication.
 *
 * @author André Diogo
 * @version 1.1, 29-12-2017
 */
public class RegisterReply implements CatalystSerializable {
    boolean hasSucceeded;

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
