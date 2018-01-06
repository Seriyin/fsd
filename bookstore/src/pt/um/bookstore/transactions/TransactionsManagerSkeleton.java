package pt.um.bookstore.transactions;

import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.transport.Connection;
import pt.haslab.ekit.Log;
import pt.um.bookstore.handlers.transactions.HeartBeatHandler;
import pt.um.bookstore.messaging.transactions.AbortRequest;
import pt.um.bookstore.messaging.transactions.CommitRequest;
import pt.um.bookstore.util.DistObjManager;
import pt.um.bookstore.util.RemoteObj;
import pt.um.bookstore.util.Skeleton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A {@link pt.um.bookstore.transactions.TransactionsManager} manager implementation.
 */
public class TransactionsManagerSkeleton extends Skeleton implements TransactionsManager
{
    private DistObjManager dom;
    private Log coord;
    private Map<Long,Transaction> mr;
    private AtomicLong l;

    public TransactionsManagerSkeleton(DistObjManager dom)
    {
        this.dom = dom;
        l = new AtomicLong(0);
        coord = new Log("coord");
        mr = new HashMap<>();
        openLog();
        setRef(dom.exportRef(this).orElse(null));
    }

    public void openLog()
    {
        //Set handlers for TransactionManager's log.
        coord.handler(HeartBeat.class, new HeartBeatHandler(this));
        //Wait till coord is consistent.
        coord.open().join();
    }

    @Override
    public long begin()
    {
        long xid = l.incrementAndGet();
        mr.put(xid, new Transaction(xid));
        coord.append(xid);
        return xid;
    }

    @Override
    public <T extends CatalystSerializable> void heartbeat(long xid, RemoteObj ro, T logged)
    {
        Transaction t = mr.get(xid);
        HeartBeat<T> hb = new HeartBeat<>(xid, ro, logged);
        //if repeated heartbeat, resource reboot, no problem.
        if(t.getHeartbeats().contains(hb)) {
            t.registerHeartBeat(dom.getConnection(ro.getAddress()),hb);
        }
        coord.append(hb);
    }

    @Override
    public void prepare(long xid) {
        mr.get(xid).ready();
        coord.append(new Prepare(xid));
    }

    @Override
    public void prepared(long xid, RemoteObj ro)
    {
        Prepared p = new Prepared(xid, ro);
        //prepare after commit was already fired,
        // client reboot, refire commit.
        if(mr.get(xid).prepare(p))
        {
            if(mr.get(xid).aborted())
            {
                abort(xid, p.getRef());
            }
            else {
                commit(xid, p.getRef());
            }
        }
        else {
            coord.append(p);
            mr.get(xid).update();
            if(mr.get(xid).finalized())
            {
                if(mr.get(xid).aborted())
                {
                    abort(xid);
                }
                else {
                    commit(xid);
                }
            }
        }
    }



    /**
     * Send commit to all resources after prepared responses arrive from them.
     * @param xid The transaction id.
     */
    private void commit(long xid) {
        for(Connection r : mr.get(xid).getConnections())
        {
            CommitRequest rq = new CommitRequest(xid);
            r.send(rq);
        }
    }

    /**
     * Resend commit on client failure.
     * @param xid The transaction id.
     * @param ref The remote reference.
     */
    private void commit(long xid, RemoteObj ref)
    {
        dom.getConnection(ref.getAddress()).send(new CommitRequest(xid));
    }


    /**
     * Send abort to all resources after an abort request arrives from one of them.
     * <p>
     * Can also happen on coordinator restart before 2PC.
     * @param xid The transaction id.
     */
    @Override
    public void abort(long xid)
    {
        for(Connection r : mr.get(xid).getConnections())
        {
            AbortRequest rq = new AbortRequest(xid);
            r.send(rq);
        }
    }

    /**
     * Send abort to a single resource after a prepared request arrives late.
     * <p>
     * Can also happen on coordinator restart before 2PC.
     * @param xid The transaction id.
     * @param ref The transaction ref.
     */
    private void abort(long xid, RemoteObj ref)
    {
        AbortRequest rq = new AbortRequest(xid);
        dom.getConnection(ref.getAddress()).send(rq);
    }

}
