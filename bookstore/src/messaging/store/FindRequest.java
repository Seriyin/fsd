package messaging.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import messaging.util.ObjRequest;
import util.RemoteObj;

import java.util.ArrayList;
import java.util.List;

/**
 * FindRequest can be constructed in multiple ways to reflect multiple find possibilities in store interface.
 * <p>
 * Covers all intended store use cases.
 */
public class FindRequest extends ObjRequest
{
    private String name;
    private List<String> authors;
    private long isbn;

    public FindRequest(RemoteObj ro, String name)
    {
        super(ro);
        this.name = name;
        this.authors = new ArrayList<>();
        this.isbn = -1;
    }

    public FindRequest(RemoteObj ro, String name, List<String> authors)
    {
        super(ro);
        this.name = name;
        this.authors = authors;
        this.isbn = -1;
    }

    public FindRequest(RemoteObj ro, List<String> authors)
    {
        super(ro);
        this.authors = authors;
        this.name = null;
        this.isbn = -1;
    }

    public FindRequest(RemoteObj ro, long isbn)
    {
        super(ro);
        this.authors = new ArrayList<>();
        this.name = null;
        this.isbn = isbn;
    }


    public String getName()
    {
        return name;
    }

    public List<String> getAuthors()
    {
        return authors;
    }

    public long getISBN()
    {
        return isbn;
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
        authors = serializer.readObject(buffer);
        name = buffer.readString();
        isbn = buffer.readLong();
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        super.writeObject(buffer, serializer);
        serializer.writeObject(authors,buffer);
        buffer.writeString(name);
        buffer.writeLong(isbn);
    }
}
