package store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;


/**
 * Store item relates to cart as the item selected + quantity.
 */
public class Item<T extends CatalystSerializable> implements CatalystSerializable
{
    private T t;
    private int quantity;

    public Item(T t)
    {
        this.t = t;
        quantity = 1;
    }

    public Item(T t, int quantity)
    {
        this.t = t;
        this.quantity = quantity;
    }

    public T getItem()
    {
        return t;
    }

    public int getQuantity()
    {
        return quantity;
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        serializer.writeObject(t,buffer);
        buffer.writeInt(quantity);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        t = serializer.readObject(buffer);
        quantity = buffer.readInt();
    }
}
