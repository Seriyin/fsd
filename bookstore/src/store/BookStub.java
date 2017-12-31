package store;

import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import util.RemoteObj;
import util.Stub;

import java.util.List;

/**
 * #TODO Add requests.
 * BookStub fires off requests for info on a book as needed and caches them.
 * <p>
 * Will allow requests for entire book copy or info only as needed.
 */
public class BookStub extends Stub implements Book {
    private List<String> authors;
    private String title;
    private long isbn;
    private double price;

    /**
     * Constructor for Stub takes in RemoteObj and Transport.
     *
     * @param ro RemoteObj of the actual object.
     * @param c Connection over which to send requests.
     */
    public BookStub(RemoteObj ro, Connection c) {
        super(ro, c);
    }


    @Override
    public long getISBN() {
        return 0;
    }

    @Override
    public List<String> getAuthors() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public double getPrice() {
        return 0;
    }
}
