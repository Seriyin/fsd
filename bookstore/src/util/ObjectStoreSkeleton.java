package util;

import java.util.*;

/**
 * ObjectStoreSkeleton implements the ObjectStore interface with no excepts.
 */
public class ObjectStoreSkeleton<T> implements ObjectStore<T> {
    private Map<String, Map<Long, T>> mp;


    @Override
    public Optional<T> getObject(String name, long tag)
    {
        Optional<T> result;
        if(mp.containsKey(name))
        {
            Map<Long, T> m = mp.get(name);
            if(m.containsKey(tag))
            {
                result = Optional.of(m.get(tag));
            }
            else
                result = Optional.empty();
        }
        else
            result = Optional.empty();
        return result;
    }


    @Override
    public boolean insertObject(String name, long tag, T obj) {
        boolean result = false;
        if(mp.containsKey(name)) {
            Map<Long, T> m = mp.get(name);
            if(!m.containsKey(tag)) {
                m.put(tag,obj);
                result = true;
            }
        }
        else {
            Map<Long, T> mo = new HashMap<>();
            mo.put(tag,obj);
            mp.put(name,mo);
            result = true;
        }
        return result;
    }
}
