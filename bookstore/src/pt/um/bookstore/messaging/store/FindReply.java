package pt.um.bookstore.messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.ListMessage;
import pt.um.bookstore.messaging.util.Reply;
import pt.um.bookstore.store.Book;

import java.util.List;

/**
 * A reply containing a list of books.
 * <p>
 * Might be empty, or single element.
 */
public class FindReply extends ListMessage<Book> implements Reply
{
    public FindReply(List<Book> l)
    {
        super(l);
    }

    @Override
    public void writeObject(BufferOutput buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
    }

    @Override
    public void readObject(BufferInput buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
    }
}
