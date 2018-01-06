package pt.um.bookstore.handlers.store;

import pt.um.bookstore.handlers.util.WithReplyHandler;
import pt.um.bookstore.messaging.store.FindReply;
import pt.um.bookstore.messaging.store.FindRequest;
import pt.um.bookstore.store.Book;
import pt.um.bookstore.store.Store;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * A handler that constructs a {@link FindReply} from a {@link FindRequest}
 * in a store, finding the needed list of books according to search parameters.
 */
public class FindRequestHandler implements WithReplyHandler<FindRequest,FindReply>
{
    private Store store;

    public FindRequestHandler(Store store)
    {
        this.store = store;
    }


    @Override
    public CompletableFuture<FindReply> apply(FindRequest rq)
    {
        List<Book> lb;
        if(rq.getISBN()!=-1)
        {
            Optional<Book> b = store.findByISBN(rq.getISBN());
            lb = new ArrayList<>();
            b.ifPresent(lb::add);
        }
        else if(rq.getAuthors().isEmpty())
        {
            lb = store.findByTitle(rq.getName());
        }
        else if(rq.getAuthors().size()==1 && rq.getName()!=null)
        {
            lb = store.findByTitleAndAuthor(rq.getAuthors().get(0),rq.getName());
        }
        else if(rq.getAuthors().size()==1)
        {
            lb = store.findByAuthor(rq.getAuthors().get(0));
        }
        else if(rq.getName()!=null)
        {
            lb = store.findByTitleAndAuthors(rq.getName(),rq.getAuthors());
        }
        else {
            lb = store.findByAuthors(rq.getAuthors());
        }
        return CompletableFuture.completedFuture(new FindReply(lb));
    }
}
