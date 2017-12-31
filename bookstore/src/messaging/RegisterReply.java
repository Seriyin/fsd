package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

/**
 * Register reply is a simple reply to a naming service registration,
 * with a success indication.
 */
public class RegisterReply extends SReply implements CatalystSerializable {
    public RegisterReply(boolean hasSucceeded) {
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
