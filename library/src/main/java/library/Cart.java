package library;

/**
 * The Cart interface describes operations that can be requested
 * over a Cart.
 * <p>
 * These include buying all the cart items and adding or removing from the cart.
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.0, 20-12-2017
 */
public interface Cart {

    boolean add(Book b);
    boolean remove(Book b);
    boolean buy();
}