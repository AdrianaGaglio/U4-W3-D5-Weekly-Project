package epicode.it.entity.book.dao;

import epicode.it.entity.book.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BookDAO {
    private EntityManager em;

    public void save(Book book) {
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
    }

    public void saveAll(List<Book> books) {
        em.getTransaction().begin();
        for (Book book : books) {
            em.persist(book);
        }
        em.getTransaction().commit();
    }

    public List<Book> getAll() {
        return this.em.createNamedQuery("findAll_Book", Book.class).getResultList();
    }

    public Book getById(Long id) {
        return em.find(Book.class, id);
    }

    public void update(Book book) {
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    public void delete(Book book) {
        em.getTransaction().begin();
        em.remove(book);
        em.getTransaction().commit();
    }
}