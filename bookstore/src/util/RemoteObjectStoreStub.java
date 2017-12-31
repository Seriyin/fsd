package util;

import io.atomix.catalyst.transport.Connection;

import java.util.Optional;

/**
 * RemoteObjectStoreStub will cache the latest RemoteObj retrieved.
 * <p>
 * It is a class that fires off requests to a known object store
 * to store or retrieve unique-named objects.
 */
public class RemoteObjectStoreStub extends Stub implements RemoteObjectStore {
    private RemoteObj cached;


    public RemoteObjectStoreStub(RemoteObj b, Connection c) {
        super(b,c);
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
