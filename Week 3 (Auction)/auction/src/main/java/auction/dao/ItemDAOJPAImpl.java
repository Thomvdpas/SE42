package auction.dao;

import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Martijn on 18-05-17.
 */
public class ItemDAOJPAImpl implements ItemDAO {

    // EntityManager
    EntityManager entityManager;

    public ItemDAOJPAImpl(EntityManager em) {
        entityManager = em;
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("Item.count");
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(Item item) {
        entityManager.getTransaction().begin();
        entityManager.persist(item);
        entityManager.getTransaction().commit();
    }

    @Override
    public void edit(Item item) {
        entityManager.getTransaction().begin();
        entityManager.merge(item);
        entityManager.getTransaction().commit();
    }

    @Override
    public Item find(Long id) {
        return entityManager.find(Item.class, id);
    }

    @Override
    public List<Item> findAll() {
        Query q = entityManager.createNamedQuery("Item.getAll");
        return q.getResultList();
    }

    @Override
    public List<Item> findByDescription(String description) {
        List<Item> foundItems = null;
        Query q = entityManager.createNamedQuery("Item.findByDescription", Item.class);
        q.setParameter("description", description);

        try {
            foundItems = (List<Item>) q.getResultList();
        }

        catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return foundItems;
    }

    @Override
    public void remove(Item item) {
        entityManager.getTransaction().begin();
        entityManager.remove((entityManager.merge(item)));
        entityManager.getTransaction().commit();
    }

}
