package pt.um.bookstore.handlers.store;

import pt.um.bookstore.handlers.util.LogHandler;
import pt.um.bookstore.store.Accounts;
import pt.um.bookstore.store.Purchase;

public class PurchaseHandler implements LogHandler<Purchase>
{
    private Accounts accounts;

    public PurchaseHandler(Accounts accounts)
    {
        this.accounts = accounts;
    }

    @Override
    public void accept(Integer integer, Purchase purchase)
    {
        accounts.getAccounts()
                .get(purchase.getCID())
                .addPurchase(purchase);
    }
}
