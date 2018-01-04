package bank;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;

import java.util.Objects;

/**
 * Item is a simple immutable object containing an item description,
 * quantity and value charged for that item.
 */
public class Item implements CatalystSerializable {
    private String item;
    private int quantity;
    private double charge;
    private double totalCharge;

    public Item(String item, double charge, int quantity) {
        this.item = item;
        this.charge = charge;
        this.quantity = quantity;
        totalCharge = charge * quantity;
    }

    public String getItem() {
        return item;
    }

    public double getCharge() {
        return charge;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public double getTotalCharge()
    {
        return totalCharge;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Item item1 = (Item) o;
        return getQuantity() == item1.getQuantity() &&
               Double.compare(item1.getCharge(), getCharge()) == 0 &&
               Double.compare(item1.getTotalCharge(), getTotalCharge()) == 0 &&
               Objects.equals(getItem(), item1.getItem());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getItem(), getQuantity(), getCharge(), getTotalCharge());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item { item='")
          .append(item)
          .append("', charge per item=")
          .append(charge)
          .append(", quantity=")
          .append(quantity)
          .append(", total charge=")
          .append(totalCharge)
          .append(" }");
        return sb.toString();
    }

    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer) {
        buffer.writeString(item);
        buffer.writeDouble(charge);
        buffer.writeInt(quantity);
        buffer.writeDouble(totalCharge);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer) {
        item = buffer.readString();
        charge = buffer.readDouble();
        quantity = buffer.readInt();
        totalCharge = buffer.readDouble();
    }
}
