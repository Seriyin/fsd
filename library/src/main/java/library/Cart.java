package library;

/**
 * API of a cart.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.0
 */
public interface Cart {

    /**
     * Adds a book to the cart.
     * @param isbn ISBN of the book to add.
     * @return Error code.
     */
    int addBook(int isbn);

    /**
     * Removes a book from the cart.
     * @param isbn ISBN of the book to remove.
     * @return Error code.
     */
    int removeBook(int isbn);

    /**
     * Finalizes shopping by buying all books in the cart.
     */
    void buy();
}
