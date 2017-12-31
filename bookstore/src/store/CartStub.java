package store;

import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import util.RemoteObj;
import util.Stub;

public class CartStub extends Stub implements Cart {

    public CartStub(RemoteObj ro, Connection c) {
        super(ro, c);
    }

    @Override
    public boolean add(Book b) {
        return false;
    }

    @Override
    public boolean add(long isbn) {
        return false;
    }

    @Override
    public boolean remove(Book b) {
        return false;
    }

    @Override
    public boolean remove(long isbn) {
        return false;
    }

    @Override
    public boolean buy() {
        return false;
    }
}
