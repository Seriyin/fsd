package store;

import io.atomix.catalyst.transport.Transport;
import util.RemoteObj;
import util.Stub;

import java.util.List;

/**
 * #TODO Add requests.
 * BookStub fires of requests for info on a book as needed.
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
     * @param t  Transport over which to connect.
     */
    public BookStub(RemoteObj ro, Transport t) {
        super(ro, t);
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
