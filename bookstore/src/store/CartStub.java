package store;

import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import messaging.*;
import util.RemoteObj;
import util.Stub;

import java.util.ArrayList;
import java.util.List;

/**
 * Cart implemented client-side.
 * <p>
 * Reduces to sending the various translated requests to
 * the known cart.
 */
public class CartStub extends Stub implements Cart {
    private List<Long> cached;

    public CartStub(RemoteObj ro, Connection c) {
        super(ro, c);
        cached = new ArrayList<>();
    }

    /**
     * Adds via isbn of the book.
     * @param b book to add.
     * @return whether the book was successfully added to cart.
     * @see AddRequest
     * @see AddReply
     */
    @Override
    public boolean add(Book b)
    {
        boolean result = true;
        if(!cached.contains(b.getISBN())) {
            AddRequest arq = new AddRequest(getRef(), b.getISBN());
            result = getConnection().<AddRequest, AddReply>sendAndReceive(arq)
                                    .join()
                                    .hasSucceeded();
            if(result){
                cached.add(b.getISBN());
            }
        }
        return result;
    }

    /**
     * Adds via isbn of the book to the remote cart.
     * @param isbn ISBN of the book to add.
     * @return whether the book was successfully added to the remote cart.
     * @see AddRequest
     * @see AddReply
     */
    @Override
    public boolean add(long isbn) {
        boolean result = true;
        if(!cached.contains(isbn))
        {
            AddRequest arq = new AddRequest(getRef(),isbn);
            result = getConnection().<AddRequest, AddReply>sendAndReceive(arq)
                                    .join()
                                    .hasSucceeded();
            if(result){
                cached.add(isbn);
            }
        }
        return result;
    }

    /**
     * Removes via isbn of the book in the remote cart.
     * @param b the book to remove.
     * @return whether the book was successfully removed from the remote cart.
     * @see RemoveRequest
     * @see RemoveReply
     */
    @Override
    public boolean remove(Book b)
    {
        boolean result = true;
        int i = cached.lastIndexOf(b.getISBN());
        if(i!=-1){
            RemoveRequest rrq = new RemoveRequest(getRef(),b.getISBN());
            result = getConnection().<RemoveRequest, RemoveReply>sendAndReceive(rrq)
                                    .join()
                                    .hasSucceeded();
            if(result) {
                cached.remove(i);
            }
        }
        return result;
    }

    /**
     * Removes via isbn of the book in the remote cart.
     * @param isbn ISBN of the book to remove.
     * @return whether the book was successfully removed from the remote cart.
     * @see RemoveRequest
     * @see RemoveReply
     */
    @Override
    public boolean remove(long isbn) {
        boolean result = true;
        int i = cached.lastIndexOf(isbn);
        if(i!=-1)
        {
            RemoveRequest rrq = new RemoveRequest(getRef(),isbn);
            result = getConnection().<RemoveRequest, RemoveReply>sendAndReceive(rrq)
                                    .join()
                                    .hasSucceeded();
            if(result) {
                cached.remove(i);
            }
        }
        return result;
    }

    /**
     * Attempts to buy books in the remote cart.
     * @return whether the books were successfully purchased.
     * @see BuyRequest
     * @see BuyReply
     */
    @Override
    public boolean buy() {
        boolean result = true;
        if(!cached.isEmpty()) {
            BuyRequest rrq = new BuyRequest(getRef());
            result = getConnection().<BuyRequest, BuyReply>sendAndReceive(rrq)
                                    .join()
                                    .hasSucceeded();
            if(result) {
                cached.clear();
            }
        }
        return result;
    }
}
