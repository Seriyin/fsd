package util;

import io.atomix.catalyst.transport.Transport;

import java.util.Optional;

/**
 * ObjectStoreStub
 * @author André Diogo, <redan.blue27@gmail.com>
 * @version 1.2, 29-12-2017
 */
public class ObjectStoreStub extends Stub implements ObjectStore {
    private RemoteObj cached;


    public ObjectStoreStub(RemoteObj b, Transport t) {
        super(b,t);
        cached = null;
    }

    @Override
    public Optional<RemoteObj> getObject(String name, long tag) {
        return Optional.empty();
    }

    @Override
    public Optional<RemoteObj> getObject(String name) {
        return Optional.empty();
    }

    @Override
    public boolean insertObject(String name, RemoteObj ro) {
        return false;
    }
}
