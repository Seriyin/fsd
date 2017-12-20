package library;

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
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.0, 20-12-2017
 */
public interface Store {
    Optional<List<Book>> findByTitle(String title);
    Optional<List<Book>> findByAuthor(String author);
    Optional<List<Book>> findByAuthor(List<String> authors);
    Optional<List<Book>> findByISBN(long isbn);
    Optional<List<Book>> findByTitleAndAuthor(String title, String author);
    Optional<List<Book>> findByTitleAndISBN(String title, long isbn);
    Optional<List<Book>> browse(int page);
    Optional<List<Book>> getPurchased(int cid);
}