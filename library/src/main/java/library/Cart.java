package library;

/**
 * The Cart interface describes operations that can be requested
 * over a Cart.
 * <p>
 * These include buying all the cart items and adding or removing from the cart.
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.1, 20-12-2017
 */
public interface Cart {


    /**
     * Adds a book to the cart.
     * @param b book to add.
     * @return whether it succeeded.
     */
    boolean add(Book b);

    /**
     * Adds a book to the cart.
     * @param isbn ISBN of the book to add.
     * @return whether it succeeded.
     */
    boolean add(long isbn);

    /**
     * Removes a book from the cart.
     * @param b the book to remove.
     * @return whether it succeeded.
     */
    boolean remove(Book b);

    /**
     * Removes a book from the cart.
     * @param isbn ISBN of the book to remove.
     * @return whether it succeeded.
     */
    boolean remove(long isbn);

    /**
     * Finalizes shopping by buying all books in the cart.
     * @return whether it succeeded.
     */
    boolean buy();
}