package pt.um.bookstore.store;

import pt.um.bookstore.bank.Bank;
import pt.um.bookstore.util.NamingService;

import java.util.List;
import java.util.Optional;

/**
 * Store is the interface that describes book store operations.
 * <p>
 * Defines a couple find methods which might return zero or more results.
 * <p>
 * Defines a paged-browsing operation, to allow book browsing.
 * <p>
 * Defines an operation to return books bought to date by a given client.
 */
public interface Store {
    /**
     * Search the {@link Store} by title.
     * @param title Title to search.
     * @return Future of a list of none or more books matching the title.
     */
    List<Book> findByTitle(String title);

    /**
     * Search the {@link Store} by author.
     * @param author Name of the author to search.
     * @return Future of a list of none or more books matching the author.
     */
    List<Book> findByAuthor(String author);

    /**
     * Search the {@link Store} by authors.
     * @param authors List of author names to search.
     * @return List of none or more books matching the author.
     */
    List<Book> findByAuthors(List<String> authors);

    /**
     * Search the {@link Store} by isbn.
     * @param isbn ISBN to search.
     * @return The book or null.
     */
    Optional<Book> findByISBN(long isbn);

    /**
     * Search the {@link Store} by author and title.
     * @param title Title of the book to search.
     * @param author Name of the author to search.
     * @return List of none or more books matching the author.
     */
    List<Book> findByTitleAndAuthor(String title, String author);

    /**
     * Search the {@link Store} by authors and title.
     * @param title Title of the book to search.
     * @param authors List of author names to search.
     * @return List of none or more books matching the author.
     */
    List<Book> findByTitleAndAuthors(String title, List<String> authors);

    /**
     * Lists a page of existing books in the {@link Store}.
     * @param page the page number to grab.
     * @return List containing a page of books in the {@link Store}.
     */
    List<Book> browse(int page);

    /**
     * Lists all existing books in the {@link Store}.
     * @return List containing all the books in the {@link Store}.
     */
    List<Book> listBooks();

    /**
     * Lists all purchases by a client in the {@link Store}.
     * @param cid The client's id.
     * @return List containing all the books purchased organized by purchases
     *         (in lists of {@link Item}) by the client in the {@link Store}.
     */
    List<Purchase> getPurchased(long cid);

    /**
     * Lists a purchases by a client in the {@link Store}.
     * @param cid The client's id.
     * @param page Index of purchase in descending order based on time.
     * @return List containing all the books purchased page purchases ago.
     */
    Optional<Purchase> getPagePurchase(long cid, int page);

    /**
     * Add a new client account in the {@link Store}.
     * @param user A unique username.
     * @param bank An existing pt.um.bookstore.bank known by a {@link NamingService}.
     * @param bankcid The client id in that {@link Bank}.
     * @return whether the account was successfully created.
     */
    boolean addClientAccount(String user, String bank, long bankcid);

    /**
     * Add a rebuilt from log account to the {@link Store}.
     * @param ac Account to add.
     * @return Whether the add operation was successful.
     */
    boolean addClientAccount(Account ac);

}