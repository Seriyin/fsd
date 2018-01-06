package pt.um.bookstore.handlers.store;

import pt.um.bookstore.handlers.util.LogHandler;
import pt.um.bookstore.store.Accounts;
import pt.um.bookstore.transactions.Commit;

public class StoreCommitHandler implements LogHandler<Commit>
{
    private Accounts accounts;

    public StoreCommitHandler(Accounts accounts)
    {
        this.accounts = accounts;
    }

    public void accept(Integer integer, Commit commit)
    {
        accounts.commit(commit.getTxID());
    }
}
