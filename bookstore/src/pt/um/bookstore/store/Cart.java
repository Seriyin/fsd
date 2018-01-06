package pt.um.bookstore.store;

import java.util.List;

/**
 * The Cart interface describes operations that can be requested
 * over a Cart.
 * <p>
 * These include buying all the cart items and adding or removing from the cart.
 * <p>
 * Item is generic but the Cart is not so far. Would need a little refactoring.
 */
public interface Cart {


    /**
     * Adds a book to the cart.
     * @param b book to add.
     */
    void add(Book b);

    /**
     * Adds a number of copies of the book to the cart.
     * @param b book to add.
     * @param qt quantity to add.
     */
    void add(Book b, int qt);

    /**
     * Removes a book from the cart.
     * @param b the book to remove.
     */
    void remove(Book b);

    /**
     * Removes a number of copies from a book from the cart.
     * @param b book to remove.
     * @param qt quantity to remove.
     */
    void remove(Book b, int qt);

    /**
     * Finalizes shopping by clearing all books in the cart.
     */
    void clear();

    /**
     * View items in cart.
     * @return list of items in cart.
     */
    List<Item<Book>> view();

    /**
     * Buy items in cart.
     */
    void buy();
}