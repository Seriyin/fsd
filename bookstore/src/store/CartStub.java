package store;

import io.atomix.catalyst.transport.Connection;
import messaging.store.*;
import org.slf4j.LoggerFactory;
import util.RemoteObj;
import util.Server;
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
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Server.class);
    private List<Item<Book>> cached;

    public CartStub(RemoteObj ro, Connection c) {
        super(ro, c);
        cached = new ArrayList<>();
    }

    /**
     * Sends a request to add a book to the remote cart.
     * @param b book to add.
     * @see AddRequest
     */
    @Override
    public void add(Book b)
    {
        add(b,1);
    }

    /**
     * Sends a request to add a book to the remote cart.
     * @param b book to add.
     * @param qt quantity of books to add.
     * @see AddRequest
     */
    @Override
    public void add(Book b, int qt)
    {
        AddRequest arq = new AddRequest(getRef(), b, qt);
        getConnection().send(arq);
    }

    /**
     * Sends a request to remove book in the remote cart.
     * @param b the book to remove.
     * @see RemoveRequest
     */
    @Override
    public void remove(Book b)
    {
        remove(b,1);
    }


    /**
     * Sends a request to remove book in the remote cart.
     * @param b the book to remove.
     * @param qt quantity of books to remove.
     * @see RemoveRequest
     */
    @Override
    public void remove(Book b, int qt)
    {
        RemoveRequest rrq = new RemoveRequest(getRef(), b, qt);
        getConnection().send(rrq);
    }



    /**
     * Fires off a request to clear the cart.
     * @see BuyRequest
     * @see BuyReply
     */
    @Override
    public void clear() {
        ClearRequest rrq = new ClearRequest(getRef());
        getConnection().send(rrq);
    }

    /**
     * View items in cart.
     * <p>
     * No guarantees it is immediately coherent with remote cart.
     * @return list of items in cart.
     */
    @Override
    public List<Item<Book>> view()
    {
        return cached;
    }
}
