package epicode.it.entity.magazine.dao;

import epicode.it.entity.magazine.Magazine;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MagazineDAO {
    private EntityManager em;

    public void save(Magazine magazine) {
        em.getTransaction().begin();
        em.persist(magazine);
        em.getTransaction().commit();
    }

    public void saveAll(List<Magazine> magazines) {
        em.getTransaction().begin();
        for (Magazine magazine : magazines) {
            em.persist(magazine);
        }
        em.getTransaction().commit();
    }

    public List<Magazine> getAll() {
        return this.em.createNamedQuery("findAll_Magazine", Magazine.class).getResultList();
    }

    public Magazine getById(Long id) {
        return em.find(Magazine.class, id);
    }

    public void update(Magazine magazine) {
        em.getTransaction().begin();
        em.merge(magazine);
        em.getTransaction().commit();
    }

    public void delete(Magazine magazine) {
        em.getTransaction().begin();
        em.remove(magazine);
        em.getTransaction().commit();
    }
}