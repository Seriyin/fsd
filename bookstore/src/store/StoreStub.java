package store;

import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import util.RemoteObj;
import util.Stub;

import java.util.List;
import java.util.Optional;

/**
 * TODO Implement consultations.
 */
public class StoreStub extends Stub implements Store {

    /**
     * Constructor for Stub takes in RemoteObj and Connection.
     *
     * @param ro RemoteObj of the actual object.
     * @param c Connection over which to send requests.
     */
    public StoreStub(RemoteObj ro, Connection c) {
        super(ro, c);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return null;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return null;
    }

    @Override
    public List<Book> findByAuthor(List<String> authors) {
        return null;
    }

    @Override
    public Optional<Book> findByISBN(long isbn) {
        return Optional.empty();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return null;
    }

    @Override
    public List<Book> findByTitleAndAuthors(String title, List<String> authors) {
        return null;
    }

    @Override
    public List<Book> browse(int page) {
        return null;
    }

    @Override
    public List<Book> listBooks() {
        return null;
    }

    @Override
    public List<Book> getPurchased(long cid) {
        return null;
    }
}
