package epicode.it.entity.user.dao;

import epicode.it.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UserDAO {
    private EntityManager em;

    public void save(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public void saveAll(List<User> users) {
        em.getTransaction().begin();
        for (User user : users) {
            em.persist(user);
        }
        em.getTransaction().commit();
    }

    public List<User> getAll() {
        return this.em.createNamedQuery("findAll_User", User.class).getResultList();
    }

    public User getById(Long id) {
        return em.find(User.class, id);
    }

    public void update(User user) {
        em.getTransaction().begin();
        em.merge(user);
        em.getTransaction().commit();
    }

    public void delete(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
    }
}