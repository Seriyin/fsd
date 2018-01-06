package pt.um.bookstore.transactions;

import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.transport.Connection;

import java.util.HashSet;
import java.util.Set;

public class Transaction
{
    private Set<Connection> scn;
    private Set<HeartBeat<?>> shb;
    private Set<Prepared> prepare;
    private long id;
    private boolean inProgress;
    private boolean completed;
    private boolean aborted;

    public Transaction(long id)
    {
        scn = new HashSet<>();
        shb = new HashSet<>();
        this.id = id;
        this.inProgress = false;
        this.completed = completed;
        this.aborted = false;
    }

    /**
     * Ready the transaction for 2PC.
     */
    public void ready()
    {
        inProgress = true;
    }

    public Set<Connection> getConnections()
    {
        return scn;
    }

    public Set<HeartBeat<?>> getHeartbeats()
    {
        return shb;
    }

    public boolean prepare(Prepared p)
    {
        boolean result = false;
        if(completed)
        {
            result=true;
        }
        else {
            prepare.add(p);
        }
        return result;
    }

    /**
     * Abort the transaction
     */
    public void abort()
    {
        if(!completed) {
            aborted = true;
            completed = true;
        }
        else {
            //This should never happen.
            //LOG or throw.
        }
    }

    public long getXID()
    {
        return id;
    }

    public <T extends CatalystSerializable> void registerHeartBeat(Connection c, HeartBeat<T> hb)
    {
        scn.add(c);
        shb.add(hb);
    }

    /**
     * Update state after a prepared response comes from a resource.
     */
    public void update() {
        completed = prepare.size()==shb.size();
    }

    /**
     *
     * @return if the transaction is ready for commit.
     */
    public boolean finalized()
    {
        return completed;
    }

    /**
     *
     * @return if the transaction is to be aborted.
     */
    public boolean aborted()
    {
        return aborted;
    }
}
