package bank;


import io.atomix.catalyst.concurrent.ThreadContext;
import io.atomix.catalyst.serializer.Serializer;
import io.atomix.catalyst.transport.Connection;
import io.atomix.catalyst.transport.Transport;
import messaging.BuyReply;
import messaging.BuyRequest;
import messaging.ConsultReply;
import messaging.ConsultRequest;
import util.Server;

import java.util.function.Consumer;

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
        b = new BankSkeleton(name,getTransport(),getOwnAddress());
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
     * @see BuyRequest
     * @see BuyReply
     * @see ConsultRequest
     * @see ConsultReply
     */
    @Override
    protected void registerSerializables(){
        Serializer sr = getSerializer();
        sr.register(BuyRequest.class);
        sr.register(BuyReply.class);
        sr.register(ConsultRequest.class);
        sr.register(ConsultReply.class);
    }


    /**
     * Register handlers for all possible stand-alone replies.
     * @param c The open {@link Transport#server()} connection.
     * @see Connection#send(Object)
     */
    @Override
    protected void handlers(Connection c)
    {
        c.handler(BuyReply.class,new BuyReplyHandler());
        c.handler(ConsultReply.class,new ConsultReplyHandler());
    }

    /**
     * #TODO implement BuyReplyHandler.
     */
    private class BuyReplyHandler implements Consumer<BuyReply> {

        @Override
        public void accept(BuyReply buyReply) {
        }
    }

    /**
     * #TODO implement ConsultReplyHandler.
     */
    private class ConsultReplyHandler implements Consumer<ConsultReply> {
        @Override
        public void accept(ConsultReply consultReply) {

        }
    }

}
