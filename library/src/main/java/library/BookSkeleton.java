package library;

import java.util.List;
import java.util.Objects;

/**
 * BookSkeleton class contains a book's metadata.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.0
 */
public class BookSkeleton implements Book {

    private long isbn;
    private String title;
    private List<String> authors;
    private double price;

    public BookSkeleton(long isbn, String title, List<String> authors, double price) {
        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.price = price;
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
