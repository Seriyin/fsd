package messaging.bank;

import bank.Payment;
import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.Serializer;
import messaging.util.ListReply;

import java.util.List;

/**
 * Replies with the list of payments the client has made in the bank.
 * <p>
 * Is immutable and does not mutate the underlying list.
 */
public class ConsultReply extends ListReply<Payment>
{

    /**
     * Does not matter if the list has been cloned or not.
     * <p>
     * Will be serialized.
     * @param lp The list of payments consulted.
     */
    public ConsultReply(final List<Payment> lp)
    {
        super(lp);
    }

    public List<Payment> getPaymentHistory()
    {
        return getList();
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        super.writeObject(buffer,serializer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        super.readObject(buffer, serializer);
    }
}
