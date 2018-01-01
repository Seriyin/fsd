package bank;


import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import messaging.*;
import util.DistObjManager;
import util.Server;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * BankServer class contains the main logic to boot up a process in a connected
 * network.
 * @see pt.haslab.ekit.Clique
 */
public class BankServer extends Server {
    private String name;
    private Bank b;


    /**
     * BankServer constructor must supply a
     * bank name.
     * <p>
     * Should follow bank_{name} convention but is
     * not required.
     * @param name The name of the bank to register
     */
    public BankServer(String name)
    {
        super(name);
        b = new BankSkeleton(name,getDom());
    }

    /**
     * The main creates a BankServer and executes it.
     * <p>
     * Expects bank name as first argument,
     * otherwise defaults to bank_default.
     * <p>
     * Please follow this naming convention: bank_{name}
     * @param args shell arguments passed in.
     */
    public static void main(String args[]) {
        String bankName;
        if(args.length>0) {
            bankName = args[0];
        }
        else {
            bankName = "bank_default";
        }
        BankServer b = new BankServer(bankName);
        //Try to setup the server
        b.setup();
        //Execute the server
        b.execute();
    }

    /**
     * #TODO bank server execution.
     * Bank server execution is defined here.
     */
    @Override
    protected void execute()
    {
        ThreadContext tc = getThreadContext();
        tc.execute(() -> {

        });
    }

    /**
     * Registers all the used Request and Replies into
     * the ThreadContext's serializer.
     *
     * @see PurchaseRequest
     * @see PurchaseReply
     * @see ConsultRequest
     * @see ConsultReply
     */
    @Override
    protected void registerSerializables(){
        Serializer sr = getSerializer();
        sr.register(PurchaseRequest.class);
        sr.register(PurchaseReply.class);
        sr.register(ConsultRequest.class);
        sr.register(ConsultReply.class);
    }


    /**
     * Register handlers for all possible stand-alone requests.
     * @param c The open {@link Transport#server()} connection.
     * @see Connection#send(Object)
     */
    @Override
    protected void handlers(Connection c)
    {
        c.handler(PurchaseRequest.class,new PurchaseRequestHandler());
        c.handler(ConsultRequest.class,new ConsultRequestHandler());
    }

    /**
     * TODO including 2PC steps for registration.
     */
    private class PurchaseRequestHandler
            implements Function<PurchaseRequest, CompletableFuture<PurchaseReply>>
    {

        @Override
        public CompletableFuture<PurchaseReply> apply(PurchaseRequest rq)
        {
            b.register(rq.getClientID(),rq.getPayment());
            return CompletableFuture.completedFuture(new PurchaseReply(true));
        }
    }

    /**
     * ConsultRequest returns the payment list for the client requested.
     */
    private class ConsultRequestHandler
            implements Function<ConsultRequest,CompletableFuture<ConsultReply>>
    {
        @Override
        public CompletableFuture<ConsultReply> apply(ConsultRequest rq) {
            List<Payment> lp = b.consult(rq.getClientID());
            return CompletableFuture.completedFuture(new ConsultReply(lp));
        }
    }

}
