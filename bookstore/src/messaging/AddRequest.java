package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * AddRequest signals Cart for adding book referenced by ISBN.
 */
public class AddRequest extends ObjRequest {
    private long isbn;

    public AddRequest(RemoteObj ref, long isbn) {
        super(ref);
    }

    public long getIsbn() {
        return isbn;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
        buffer.writeLong(isbn);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        isbn = buffer.readLong();
    }
}
