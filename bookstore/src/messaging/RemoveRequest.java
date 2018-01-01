package messaging;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import util.RemoteObj;

/**
 * RemoveRequest signals Cart for removing book referenced by ISBN.
 */
public class RemoveRequest extends ObjRequest {
    private long isbn;

    public RemoveRequest(RemoteObj ref, long isbn) {
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
