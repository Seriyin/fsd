package library;

/**
 * Book class contains a book's metadata.
 * @author Andre Diogo
 * @author Diogo Pimenta
 * @version 1.0
 */
public class Book {

    private int isbn;
    private String title;
    private String author;

    public Book(int isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public Book clone() {
        return new Book(this.isbn,this.title,this.author);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("isbn=").append(isbn);
        sb.append(", title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (getIsbn() != book.getIsbn()) return false;
        if (!getTitle().equals(book.getTitle())) return false;
        return getAuthor().equals(book.getAuthor());
    }

    @Override
    public int hashCode() {
        int result = getIsbn();
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + getAuthor().hashCode();
        return result;
    }
}
