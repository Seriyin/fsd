package library;

/**
 * Store interface specifies the necessary methods of a store.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.0
 */
public interface Store {

    /**
     * Search the store by isbn.
     * @param isbn ISBN to search.
     * @return The book or null.
     */
    Book findBook(int isbn);

    /**
     * Search the store by title.
     * @param title Title to search.
     * @return Array of none or more books matching the title.
     */
    Book[] findBook(String title);

    /**
     * Search the store by author.
     * @param author Name of the author to search.
     * @return Array of none or more books matching the author.
     */
    Book[] booksByAuthor(String author);

    /**
     * Lists all existing books in the store.
     * @return Array containing all the books in the store.
     */
    Book[] listBooks();




}
