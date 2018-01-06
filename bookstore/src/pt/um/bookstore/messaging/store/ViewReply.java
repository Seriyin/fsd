package pt.um.bookstore.messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.messaging.util.ListMessage;
import pt.um.bookstore.messaging.util.Reply;
import pt.um.bookstore.store.Book;
import pt.um.bookstore.store.Item;

import java.util.List;

public class ViewReply extends ListMessage<Item<Book>> implements Reply
{
    public ViewReply(List<Item<Book>> l)
    {
        super(l);
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
    }
}
