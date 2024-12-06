package epicode.it.entity.publication.dao;

import epicode.it.entity.publication.Publication;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PublicationDAO {
    private EntityManager em;

    public void save(Publication oggetto) {
        em.getTransaction().begin();
        em.persist(oggetto);
        em.getTransaction().commit();
    }

    public void saveAll(List<Publication> publications) {
        em.getTransaction().begin();
        for (Publication publication : publications) {
            em.persist(publication);
        }
        em.getTransaction().commit();
    }

    public List<Publication> getAll() {
       return this.em.createNamedQuery("findAll_Publication", Publication.class).getResultList();
    }

    public Publication getById(Long id) {
        return em.find(Publication.class, id);
    }

    public void update(Publication publication) {
        em.getTransaction().begin();
        em.merge(publication);
        em.getTransaction().commit();
    }

    public void delete(Publication publication) {
        em.getTransaction().begin();
        em.remove(publication);
        em.getTransaction().commit();
    }
}