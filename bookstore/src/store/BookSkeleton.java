package store;

import util.DistObjManager;
import util.RemoteObj;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * BookSkeleton class contains a book's metadata.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.1
 */
public class BookSkeleton implements Book {

    private long isbn;
    private String title;
    private List<String> authors;
    private double price;
    private RemoteObj ro;


    /**
     * Constructor that builds the book without export.
     * @param isbn Book ISBN.
     * @param title Book title.
     * @param authors List of book authors.
     * @param price Price of book listing.
     */
    public BookSkeleton(long isbn, String title, List<String> authors, double price) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
        ro = null;
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
    public BookSkeleton(long isbn, String title, List<String> authors, double price, DistObjManager dom) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
        ro = dom.exportRef(this).orElse(null);
    }


    /**
     * @return if the object has already been exported.
     */
    public boolean isExported() {
        return ro!=null;
    }

    /**
     * Returns the object reference
     * @return null or RemoteObj.
     */
    public RemoteObj getRemoteObj() {
        return ro;
    }


    /**
     * Attempts to export this book to the provided DistObjManager.
     * <p>
     * If the object was already exported, does nothing and returns failure.
     * <p>
     * Otherwise tries to export the object.
     * <p>
     * Can fail if the object export fails (should never happen).
     * @param dom The DistObjManager to export object to.
     * @return whether the object was successfully exported, fills in remote object reference.
     */
    public boolean exportRef(DistObjManager dom) {
        if(isExported()) {
            return false;
        }
        else {
            Optional<RemoteObj> obj = dom.exportRef(this);
            ro = obj.orElse(null);
            return ro!=null;
        }
    }

    @Override
    public long getISBN() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> author) {
        this.authors = author;
    }

    @Override
    public BookSkeleton clone() {
        return new BookSkeleton(this.isbn,this.title,this.authors,this.price);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("isbn=").append(isbn);
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(authors).append('\'');
        sb.append('}');
        return sb.toString();
    }

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

    @Override
    public int hashCode() {

        return Objects.hash(isbn, getTitle(), getAuthors(), getPrice());
    }
}
