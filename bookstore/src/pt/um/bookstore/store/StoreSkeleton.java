package pt.um.bookstore.store;

import io.atomix.catalyst.transport.Address;
import pt.haslab.ekit.Log;
import pt.um.bookstore.bank.Bank;
import pt.um.bookstore.handlers.store.PurchaseHandler;
import pt.um.bookstore.handlers.store.StoreAccountHandler;
import pt.um.bookstore.handlers.store.StoreCommitHandler;
import pt.um.bookstore.messaging.bank.PurchaseRequest;
import pt.um.bookstore.transactions.Commit;
import pt.um.bookstore.transactions.HeartBeat;
import pt.um.bookstore.util.DistObjManager;
import pt.um.bookstore.util.NamingService;
import pt.um.bookstore.util.Skeleton;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO Logging in the pt.um.bookstore.store.
 * Store Skeleton implements an actual {@link Store}.
 */
public class StoreSkeleton extends Skeleton implements Store {
    private Set<Book> sb;
    private Accounts mb;
    private Log purchases;
    private String name;
    private DistObjManager dom;
    private Map<String,Address> knownBanks;

    public StoreSkeleton(String name, DistObjManager dom) {
        this.name = name;
        sb = new HashSet<>();
        mb = new Accounts();
        purchases = new Log(name);
        this.dom = dom;
        this.knownBanks = new HashMap<>();
        setRef(dom.exportRef(this).orElse(null));
        openLog();
    }

    private void openLog()
    {
        purchases.handler(Account.class, new StoreAccountHandler(this));
        purchases.handler(Purchase.class, new PurchaseHandler(mb));
        purchases.handler(Commit.class, new StoreCommitHandler(mb));
        purchases.open().join();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return sb.stream()
                 .filter(b -> !b.getTitle().equals(title))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return sb.stream()
                 .filter(b -> !b.getAuthors().contains(author))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthors(List<String> authors) {
        return sb.stream()
                 .filter(b -> noCommonAuthors(authors, b.getAuthors()))
                 .collect(Collectors.toList());
    }

    /**
     * Tests for common authors
     * @param authors First list.
     * @param authors1 Second list
     * @return whether there are no common authors.
     */
    private boolean noCommonAuthors(List<String> authors, List<String> authors1) {
        return authors1.stream()
                       .noneMatch(authors::contains);
    }

    @Override
    public Optional<Book> findByISBN(long isbn) {
        return sb.stream()
                 .filter(b -> b.getISBN()==isbn)
                 .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return sb.stream()
                 .filter(b -> !b.getTitle().equals(title) ||
                              !b.getAuthors().contains(author))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitleAndAuthors(String title, List<String> authors) {
        return sb.stream()
                 .filter(b -> !b.getTitle().equals(title) ||
                              noCommonAuthors(authors,b.getAuthors()))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> browse(int page) {
        List<Book> lb = new ArrayList<>();
        if(page>0) {
            if(sb.size()>(page-1)*20) {
                Iterator<Book> it = sb.iterator();
                for (int i = 0; it.hasNext() && i < (page - 1) * 20; i++) {
                    it.next();
                }
                for (int i = 0; it.hasNext() && i < 20; i++) {
                    lb.add(it.next());
                }
            }
        }
        return lb;
    }

    @Override
    public List<Book> listBooks() {
        return new ArrayList<>(sb);
    }

    @Override
    public List<Purchase> getPurchased(long cid) {
        List<Purchase> lr;
        if(mb.getAccounts().containsKey(cid))
        {
            lr = mb.getAccounts().get(cid).getPurchases();
        }
        else{
            lr = new ArrayList<>();
        }
        return lr;
    }

    /**
     * Lists a purchases by a client in the {@link Store}.
     *
     * @param cid  The client's id.
     * @param page Purchase in descending order based on time. Must be 1-indexed.
     *
     * @return List containing all the books purchased page purchases ago or empty.
     */
    @Override
    public Optional<Purchase> getPagePurchase(long cid, int page)
    {
        Optional<Purchase> purchase;
        if(mb.getAccounts().containsKey(cid))
        {
            List<Purchase> purchases;
            purchases = mb.getAccounts().get(cid).getPurchases();
            if(page>0 && purchases.size()<page)
            {
                purchase = Optional.of(purchases.get(page-1));
            }
            else {
                purchase = Optional.empty();
            }
        }
        else {
            purchase = Optional.empty();
        }
        return purchase;
    }

    /**
     * Add a new client account in the {@link Store}.
     *
     * @param user    A unique username.
     * @param bank    An existing pt.um.bookstore.bank known by a {@link NamingService}.
     * @param bankcid The client id in that pt.um.bookstore.bank.
     *
     * @return whether the account was successfully created.
     */
    @Override
    public boolean addClientAccount(String user, String bank, long bankcid)
    {
        boolean result = false;
        if(mb.getAccounts()
             .values()
             .stream()
             .map(Account::getUser)
             .noneMatch(user::equals))
        {
            long tag = mb.newTag();
            Account cl = new Account(user, bank, tag, bankcid);
            purchases.append(cl);
            result = mb.getAccounts().put(tag, cl)!=null;
        }
        return result;
    }

    /**
     * Add a rebuilt from log account to the {@link Store}.
     * @param ac Account to add.
     * @return Whether the add operation was successful.
     */
    public boolean addClientAccount(Account ac) {
        return mb.getAccounts().put(ac.getCID(),ac)!=null;
    }

    /**
     * Client is in charge of starting a transaction.
     * <p>
     * Client supplies transaction id for buy.
     * <p>
     * Buy the current selection of books from a cart.
     *
     * @param xid transaction id.
     * @param list list of items in cart.
     * @param cid client id.
     *
     * @return whether the buying operation was registered successfully.
     *         Will not be immediately available, but instead pending
     *         {@link Bank} approval.
     */
    public boolean buy(long xid, List<Item<Book>> list, long cid)
    {
        boolean result = false;
        if(mb.getAccounts().containsKey(cid))
        {
            Account a = mb.getAccounts().get(cid);
            Purchase purchase = new Purchase(cid, list);
            purchases.append(purchase);
            dom.getTransactionsManager().send(new HeartBeat<>(xid, getRef(), purchase));
            dom.getConnection(knownBanks.get(a.getBank()))
               .send(new PurchaseRequest(getRef(), a.getBankCID(), list));
            result = true;
        }
        return result;
    }


}
