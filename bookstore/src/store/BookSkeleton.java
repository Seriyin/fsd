package store;

import util.DistObjManager;
import util.Skeleton;

import java.util.List;
import java.util.Objects;

/**
 * BookSkeleton class contains a book's metadata and is immutable.
 */
public class BookSkeleton extends Skeleton implements Book {
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
    public BookSkeleton(long isbn, String title,
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
    public BookSkeleton(long isbn, String title,
                        List<String> authors, double price,
                        DistObjManager dom) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
        setRef(dom.exportRef(this).orElse(null));
    }


    @Override
    public long getISBN() {
        return isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public List<String> getAuthors() {
        return authors;
    }

    /**
     * Clone creates a book from this one with a null RemoteObj.
     * @return cloned book.
     */
    @Override
    public BookSkeleton clone() {
        return new BookSkeleton(this.isbn,this.title,this.authors,this.price);
    }

    /**
     * Pretty format of a book.
     * @return a string representing the entire book.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("isbn=").append(isbn);
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(authors).append('\'');
        sb.append('}');
        return sb.toString();
    }

    /**
     * Equals that does not check for subclasses.
     * @param o Object to compare
     * @return whether Object o is equal to this Book.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookSkeleton that = (BookSkeleton) o;
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
}
