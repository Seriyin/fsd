package util;

import io.atomix.catalyst.transport.Address;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Each process needs to manage its available distributed objects, as well as
 * leases on remote objects. This class is in charge of this.
 * Imports and Exports RemoteObjects via a RemoteObjFactory.
 * <p>
 * Right now it assumes a Address + Known Addresses.
 * @see RemoteObjFactory
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.1, 22-12-2017
 */
public final class DistObjManager {
    private RemoteObjFactory rof;
    private Map<String, Map<Long, Object>> objstr;
    private List<Address> known;
    private Address own;

    /**
     * Not using clique at all for dynamic networking.
     * <p>
     * Simple parameterized constructor receives
     * the Addresses that are known to the DistObjManager current process
     * and the actual Address of the process it's in.
     * <p>
     * We assume each process -> one server or client.
     * @param known List of known addresses.
     * @param me The process's own address.
     */
    public DistObjManager(List<Address> known, Address me) {
        this.own = own;
        objstr = new HashMap<>();
        rof = new RemoteObjFactory(own, objstr);
    }


    /**
     * TODO# Reference counting and connection handling also needs to be done.
     * Handles exporting a RemoteObj from the given object.
     * <p>
     * Delegates actual exporting to the RemoteObjFactory
     * @param obj The object to export
     * @return the RemoteObj exported or empty.
     * @see RemoteObj
     * @see RemoteObjFactory
     */
    public Optional<RemoteObj> exportRef(Object obj) {
        return rof.exportRef(obj);
    }

    /**
     * TODO# Reference counting and connection handling
     * Handles importing a stub from the given object reference.
     * <p>
     * Delegates actual importing to the RemoteObjFactory
     * @param b The object reference.
     * @return A stub of the object pointed to or empty.
     * @see RemoteObj
     * @see RemoteObjFactory
     */
    public Optional<Object> importRef(RemoteObj b) {
        return rof.importRef(b);
    }

    /**
     * Returns a clone of a skeleton pointed to by a RemoteObj.
     * <p>
     * Has to be done via reflection. (or manual monomorphization).
     * <p>
     * Excepts out the wazoo.
     * @param b the reference to be looked-up.
     * @return A clone of a skeleton or empty.
     * @see RemoteObj
     */
    public Optional<Object> importCopy(RemoteObj b) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        String cls = b.getCls();
        if(objstr.containsKey(cls)) {
            Map<Long,Object> mp = objstr.get(cls);
            if (mp.containsKey(b.getId())) {
                Object obj = mp.get(b.getId());
                //Get current class (might be Object, not entirely helpful)
                //Use as last resort.
                Class co = obj.getClass();
                //Check if it has a declared class that is more specific
                //Use that one first.
                Class sup = co.getDeclaringClass();
                Method clone;
                //Try and invoke a clone.
                if (sup!=null) {
                    clone = sup.getDeclaredMethod("clone");
                }
                else {
                    clone = co.getDeclaredMethod("clone");
                }
                return Optional.of(clone.invoke(null));
            }
        }
        return Optional.empty();
    }
}