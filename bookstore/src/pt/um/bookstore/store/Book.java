package pt.um.bookstore.store;

import io.atomix.catalyst.buffer.BufferInput;
import io.atomix.catalyst.buffer.BufferOutput;
import io.atomix.catalyst.serializer.CatalystSerializable;
import io.atomix.catalyst.serializer.Serializer;
import pt.um.bookstore.util.DistObjManager;
import pt.um.bookstore.util.Skeleton;

import java.util.List;
import java.util.Objects;

/**
 * Book class contains a book's metadata and is immutable.
 * <p>
 * Book is considered small enough to not be stubbed.
 */
public class Book extends Skeleton implements CatalystSerializable {
    private long isbn;
    private String title;
    private List<String> authors;
    private double price;


    /**
     * Constructor that builds the book without export.
     * @param isbn Book ISBN.
     * @param title Book title.
     * @param authors List of book authors.
     * @param price Price of book listing.
     */
    public Book(long isbn, String title,
                List<String> authors, double price) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
    }

    /**
     * Constructor that creates and immediately exports the object.
     * @param isbn Book ISBN.
     * @param title Book title.
     * @param authors List of book authors.
     * @param price Price of book listing.
     * @param dom DOM that keeps the ObjectReference.
     * @see DistObjManager
     */
    public Book(long isbn, String title,
                List<String> authors, double price,
                final DistObjManager dom) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
        setRef(dom.exportRef(this).orElse(null));
    }


    public long getISBN() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Clone creates a book from this one with a null RemoteObj.
     * @return cloned book.
     */
    @Override
    public Book clone()
    {
        return new Book(this.isbn,this.title,this.authors,this.price);
    }

    /**
     * Pretty format of a book.
     * @return a string representing the entire book.
     */
    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("isbn=").append(isbn);
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(authors).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Equals that does not check for subclasses or remote ref.
     * @param o Object to compare
     * @return whether Object o is equal to this Book.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book that = (Book) o;
        return isbn == that.isbn &&
                Double.compare(that.getPrice(), getPrice()) == 0 &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getAuthors(), that.getAuthors());
    }

    /**
     * Uses {@link Objects#hash(Object...)}.
     * @return hashCode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(isbn, getTitle(), getAuthors(), getPrice());
    }


    @Override
    public void writeObject(BufferOutput<?> buffer, Serializer serializer)
    {
        buffer.writeLong(isbn);
        buffer.writeDouble(price);
        buffer.writeString(title);
        serializer.writeObject(authors,buffer);
    }

    @Override
    public void readObject(BufferInput<?> buffer, Serializer serializer)
    {
        isbn = buffer.readLong();
        price = buffer.readDouble();
        title = buffer.readString();
        authors = serializer.readObject(buffer);
    }
}
