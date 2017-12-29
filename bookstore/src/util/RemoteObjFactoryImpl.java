package util;

import bank.Bank;
import bank.BankStub;
import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Transport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import store.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Class responsible for figuring out object type, generating references
 * and inserting the objects into the exported objects store.
 *
 *
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.5, 29-12-2017
 * @see RemoteObj
 * @see DistObjManager
 */
final class RemoteObjFactoryImpl implements RemoteObjFactory {
    private static final Logger LOG = LoggerFactory.getLogger(RemoteObjFactoryImpl.class);
    private Address address;
    private Map <String,Map<Long,Object>> objstr;
    private AtomicLong tag;
    private Transport t;

    /**
     * RemoteObjFactoryImpl needs to know the address in which it is
     * operating to assign to references it generates.
     * Needs the underlying store reference to populate with exported objects.
     * @param address The network address of the process's DistObjManager.
     * @param objstr The object store of the DistObjManager.
     * @see DistObjManager
     */
    RemoteObjFactoryImpl(Address address, Transport t, Map<String, Map<Long, Object>> objstr) {
        this.address = address;
        this.t = t;
        this.objstr = objstr;
        this.tag = new AtomicLong(0);
    }

    /**
     * Imports a stub from a given remote object.
     * <p>
     * Stubs are infused with the calling processes network info,
     * in order to delegate actual method invocations through RMI
     * transparently.
     * @param b The object reference
     * @return A stub or empty(in case of invalid class -> should never happen)
     * @see RemoteObj
     */
     public Optional<? extends Stub> importRef(RemoteObj b) {
        String cls = b.getCls();
        Optional<? extends Stub> result;
        if (cls.equals(Book.class.getName())) {
            result = Optional.of(new BookStub(b,t));
        }
        else if(cls.equals(Cart.class.getName())) {
            result = Optional.of(new CartStub(b,t));
        }
        else if(cls.equals(Store.class.getName())) {
            result = Optional.of(new StoreStub(b,t));
        }
        else if(cls.equals(ObjectStore.class.getName())) {
            result = Optional.of(new ObjectStoreStub(b,t));
        }
        else if(cls.equals(Bank.class.getName())) {
            result = Optional.of(new BankStub(b,t));
        }
        else if(cls.equals(TransactionsManager.class.getName())) {
            result = Optional.of(new TransactionsManagerStub(b,t));
        }
        else {
            LOG.debug("Empty import - " + cls);
            result = Optional.empty();
        }
        return result;
    }

    public Optional<RemoteObj> exportRef(Object b) {
        Optional<RemoteObj> result;
        if(b!=null) {
            Map<Long,Object> mp;
            String cls = null;
            if (b instanceof Book) {
                cls = Book.class.getName();
            }
            else if (b instanceof Cart) {
                cls = Cart.class.getName();
            }
            else if (b instanceof ObjectStore) {
                cls = ObjectStore.class.getName();
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
                if(!objstr.containsKey(cls)) {
                    objstr.put(cls,new HashMap<>());
                }
                mp = objstr.get(cls);
                long id = tag.getAndIncrement();
                mp.put(id,b);
                LOG.debug("Export Ref - "+cls);
                result = Optional.of(new RemoteObj(address,id,cls));
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