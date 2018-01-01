package store;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.Transport;
import pt.haslab.ekit.Log;
import util.DistObjManager;
import util.Skeleton;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO Logging in the store.
 * Store Skeleton implements an actual store.
 */
public class StoreSkeleton extends Skeleton implements Store {
    private Set<Book> sb;
    private Map<Long,List<Book>> mb;
    private Log purchases;
    private String name;
    private DistObjManager dom;

    public StoreSkeleton(String name, DistObjManager dom) {
        this.name = name;
        sb = new HashSet<>();
        mb = new HashMap<>();
        purchases = new Log(name);
        this.dom = dom;
        setRef(dom.exportRef(this).orElse(null));
    }

    @Override
    public List<Book> findByTitle(String title) {
        return sb.stream()
                 .filter(b -> !b.getTitle().equals(title))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return sb.stream()
                 .filter(b -> !b.getAuthors().contains(author))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(List<String> authors) {
        return sb.stream()
                 .filter(b -> !hasCommonAuthors(authors,b.getAuthors()))
                 .collect(Collectors.toList());
    }

    /**
     * Tests for common authores
     * @param authors First list.
     * @param authors1 Second list
     * @return whether there are any common authors.
     */
    private boolean hasCommonAuthors(List<String> authors, List<String> authors1) {
        boolean hasCommon = false;
        hasCommon = authors1.stream()
                            .anyMatch(authors::contains);
        return hasCommon;
    }

    @Override
    public Optional<Book> findByISBN(long isbn) {
        return sb.stream()
                 .filter(b -> b.getISBN()==isbn)
                 .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return sb.stream()
                 .filter(b -> !b.getTitle().equals(title) ||
                              !b.getAuthors().contains(author))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByTitleAndAuthors(String title, List<String> authors) {
        return sb.stream()
                 .filter(b -> !b.getTitle().equals(title) ||
                              !hasCommonAuthors(authors,b.getAuthors()))
                 .collect(Collectors.toList());
    }

    @Override
    public List<Book> browse(int page) {
        List<Book> lb = new ArrayList<>();
        if(page>0) {
            if(sb.size()>(page-1)*20) {
                Iterator<Book> it = sb.iterator();
                for (int i = 0; it.hasNext() && i < (page - 1) * 20; i++) {
                    it.next();
                }
                for (int i = 0; it.hasNext() && i < 20; i++) {
                    lb.add(it.next());
                }
            }
        }
        return lb;
    }

    @Override
    public List<Book> listBooks() {
        return new ArrayList<>(sb);
    }

    @Override
    public List<Book> getPurchased(long cid) {
        return mb.getOrDefault(cid,new ArrayList<>());
    }
}
