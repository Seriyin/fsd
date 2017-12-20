package library;

import java.util.List;

/**
 * The Book interface describes operations that can be requested
 * over a Book, mostly standard getters.
 *
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.0, 20-12-2017
 */
public interface Book {

    long getISBN();
    List<String> getAuthors();
    String getTitle();
    double getPrice();
}