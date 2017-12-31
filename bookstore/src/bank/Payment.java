package bank;

import java.util.List;
import java.util.Objects;

/**
 * Payment class contains an itemized receipt.
 */
public final class Payment {
    private List<Item> items;
    private double charge;

    public Payment(List<Item> items) {
        this.items = items;
        this.charge = items.stream().mapToDouble(Item::getCharge).sum();
    }

    public List<Item> getItems() {
        return items;
    }

    public double getCharge() {
        return charge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Double.compare(payment.getCharge(), getCharge()) == 0 &&
                Objects.equals(getItems(), payment.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems(), getCharge());
    }

    @Override
    public String toString() {
        String ln = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append("Payment {")
          .append(ln);
        items.forEach(i -> sb.append("\t").append(i).append(ln));
        sb.append("Total Charge = ")
          .append(charge)
          .append(ln)
          .append("}")
          .append(ln);
        return sb.toString();
    }
}
