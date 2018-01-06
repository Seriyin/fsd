package pt.um.bookstore.handlers.store;

import pt.um.bookstore.handlers.util.LogHandler;
import pt.um.bookstore.store.Account;
import pt.um.bookstore.store.Store;

public class StoreAccountHandler implements LogHandler<Account>
{
    private Store store;

    public StoreAccountHandler(Store store)
    {
        this.store = store;
    }

    @Override
    public void accept(Integer integer, Account account)
    {
        store.addClientAccount(account);
    }
}
