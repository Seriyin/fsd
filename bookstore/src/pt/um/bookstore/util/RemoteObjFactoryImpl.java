package pt.um.bookstore.util;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.um.bookstore.bank.Bank;
import pt.um.bookstore.bank.BankStub;
import pt.um.bookstore.store.Cart;
import pt.um.bookstore.store.CartStub;
import pt.um.bookstore.store.Store;
import pt.um.bookstore.store.StoreStub;
import pt.um.bookstore.transactions.TransactionsManager;
import pt.um.bookstore.transactions.TransactionsManagerStub;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class responsible for figuring out object type, generating references
 * and inserting the objects into the exported objects pt.um.bookstore.store.
 * @see RemoteObj
 * @see DistObjManager
 */
final class RemoteObjFactoryImpl implements RemoteObjFactory {
    private static final Logger LOG = LoggerFactory.getLogger(RemoteObjFactoryImpl.class);
    private Address address;
    private AtomicLong tag;

    /**
     * RemoteObjFactoryImpl needs to know the address in which it is
     * operating to assign to references it generates.
     * @param address The network address of the process's DistObjManager.
     * @see DistObjManager
     * @see ObjectStore
     */
    RemoteObjFactoryImpl(Address address) {
        this.address = address;
        this.tag = new AtomicLong(0);
    }

    /**
     * Imports a stub from a given remote object.
     * <p>
     * Stubs are infused with the acquired network info in the remote reference,
     * in order to delegate actual method invocations through RMI
     * transparently.
     * @param b The object reference
     * @param c The connection on which to import the stub
     * @return A stub or empty {@literal (in case of invalid class ->
     * should never happen)}
     * @see RemoteObj
     * @see Connection
     */
     public Optional<? extends Stub> importRef(RemoteObj b, Connection c) {
        String cls = b.getCls();
        Optional<? extends Stub> result;
        if(cls.equals(Cart.class.getName())) {
            result = Optional.of(new CartStub(b, c));
        }
        else if(cls.equals(Store.class.getName())) {
            result = Optional.of(new StoreStub(b, c));
        }
        else if(cls.equals(RemoteObjectStore.class.getName())) {
            result = Optional.of(new RemoteObjectStoreStub(b,c));
        }
        else if(cls.equals(Bank.class.getName())) {
            result = Optional.of(new BankStub(b, c));
        }
        else if(cls.equals(TransactionsManager.class.getName())) {
            result = Optional.of(new TransactionsManagerStub(b, c));
        }
        else if(cls.equals(RemoteObjectStore.class.getName()))
        {
            result = Optional.of(new RemoteObjectStoreStub(b,c));
        }
        else if(cls.equals(NamingService.class.getName()))
        {
            result = Optional.of(new NamingServiceStub(b,c));
        }
        else {
            LOG.debug("Empty import - " + cls);
            result = Optional.empty();
        }
        return result;
    }

    public Optional<RemoteObj> exportRef(Object b, ObjectStore<Object> objstr) {
        Optional<RemoteObj> result;
        if(b!=null) {
            Map<Long,Object> mp;
            String cls = null;
            if (b instanceof Cart) {
                cls = Cart.class.getName();
            }
            else if (b instanceof NamingService) {
                cls = NamingService.class.getName();
            }
            else if (b instanceof RemoteObjectStore) {
                cls = RemoteObjectStore.class.getName();
            }
            else if (b instanceof Store) {
                cls = Store.class.getName();
            }
            else if (b instanceof Bank) {
                cls = Bank.class.getName();
            }
            else if (b instanceof TransactionsManager) {
                cls = TransactionsManager.class.getName();
            }
            else {
                LOG.debug("Empty Ref - "+ b);
            }
            if(cls!=null) {
                long id = tag.getAndIncrement();
                if(objstr.insertObject(cls,id,b)){
                    LOG.debug("Export Ref - "+cls);
                    result = Optional.of(new RemoteObj(address,id,cls));
                }
                else {
                    LOG.debug("Export Ref failed - " + cls + " " + id);
                    result = Optional.empty();
                }
            }
            else {
                result = Optional.empty();
            }
        }
        else {
            LOG.debug("Ref null");
            result = Optional.empty();
        }
        return result;
    }

}