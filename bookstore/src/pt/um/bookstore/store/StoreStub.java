package pt.um.bookstore.store;

import io.atomix.catalyst.transport.Connection;
import pt.um.bookstore.util.NamingService;
import pt.um.bookstore.util.RemoteObj;
import pt.um.bookstore.util.Stub;

import java.util.List;
import java.util.Optional;

/**
 * TODO Implement stub.
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
    public List<Book> findByAuthors(List<String> authors) {
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
    public List<Purchase> getPurchased(long cid) {
        return null;
    }

    /**
     * Lists a purchases by a client in the store.
     *
     * @param cid  The client's id.
     * @param page Index of purchase in descending order based on time.
     *
     * @return List containing all the books purchased page purchases ago.
     */
    @Override
    public Optional<Purchase> getPagePurchase(long cid, int page)
    {
        return Optional.empty();
    }

    /**
     * Add a new client account in the store.
     *
     * @param user    A unique username.
     * @param bank    An existing bank known by a {@link NamingService}.
     * @param bankcid The client id in that pt.um.bookstore.bank.
     *
     * @return whether the account was successfully created.
     */
    @Override
    public boolean addClientAccount(String user, String bank, long bankcid)
    {
        return false;
    }

    /**
     * Add a rebuilt from log account to the store.
     *
     * @param ac Account to add.
     *
     * @return Whether the add operation was successful.
     */
    @Override
    public boolean addClientAccount(Account ac)
    {
        return false;
    }



}
