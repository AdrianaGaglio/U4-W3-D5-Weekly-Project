package epicode.it.entity.loan.dao;

import epicode.it.entity.loan.Loan;
import epicode.it.entity.publication.Publication;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class LoanDAO {
    private EntityManager em;

    public void save(Loan loan) {
        em.getTransaction().begin();
        em.persist(loan);
        em.getTransaction().commit();
    }

    public void saveAll(List<Loan> list) {
        em.getTransaction().begin();
        for (Loan obj : list) {
            em.persist(obj);
        }
        em.getTransaction().commit();
    }

    public List<Loan> getAll() {

        return this.em.createNamedQuery("findAll_Loan", Loan.class).getResultList();
    }

    public Loan getById(Long id) {
        return em.find(Loan.class, id);
    }

    public void update(Loan loan) {
        em.getTransaction().begin();
        em.merge(loan);
        em.getTransaction().commit();
    }

    public void delete(Loan loan) {
        em.getTransaction().begin();
        em.remove(loan);
        em.getTransaction().commit();
    }

    public Loan getByPublicationId(Publication publication) {
        return em.createNamedQuery("findByPublicationId", Loan.class)
                .setParameter("publication", publication).getResultStream().findFirst().orElse(null);
    }
}