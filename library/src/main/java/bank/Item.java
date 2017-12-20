package bank;

import java.util.Objects;

/**
 * Item is a simple immutable object containing an item description and a value charged for that item.
 *
 * @author André Diogo
 * @author Diogo Pimenta
 * @version 1.0, 20-12-2017
 */
public class Item {
    private String item;
    private double charge;

    public Item(String item, double charge) {
        this.item = item;
        this.charge = charge;
    }

    public String getItem() {
        return item;
    }

    public double getCharge() {
        return charge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item1 = (Item) o;
        return Double.compare(item1.getCharge(), getCharge()) == 0 &&
                Objects.equals(getItem(), item1.getItem());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getItem(), getCharge());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Item { item='")
          .append(item)
          .append("', charge=")
          .append(charge)
          .append(" }");
        return sb.toString();
    }
}
