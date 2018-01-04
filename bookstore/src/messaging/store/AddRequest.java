package messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import messaging.util.ObjRequest;
import store.Book;
import util.RemoteObj;

/**
 * AddRequest signals Cart for adding book referenced by ISBN.
 */
public class AddRequest extends ObjRequest
{
    private Book b;
    private int qt;

    public AddRequest(RemoteObj ref, Book b, int qt)
    {
        super(ref);
        this.b = b;
        this.qt = qt;
    }

    public Book getBook() {
        return b;
    }

    public int getQuantity() {
        return qt;
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer, serializer);
        serializer.writeObject(b,buffer);
        buffer.writeInt(qt);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        super.readObject(buffer, serializer);
        b = serializer.readObject(buffer);
        qt = buffer.readInt();
    }
}
