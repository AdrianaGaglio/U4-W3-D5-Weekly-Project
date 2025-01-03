package epicode.it.entity.user.dao;

import epicode.it.entity.loan.Loan;
import epicode.it.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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

    public User getByCardNumber(String cardNumber) {
        return em.createNamedQuery("findUserByCardNumber", User.class)
                .setParameter("cardNumber", cardNumber).getSingleResult();
    }

    public List<Object[]> findLoansByUser(String cardNumber) {
        TypedQuery<Object[]> query = em.createNamedQuery("findByCardNumberWithLoans", Object[].class)
                .setParameter("cardNumber", cardNumber);
        List<Object[]> results = query.getResultList();
        return results;
    }
}