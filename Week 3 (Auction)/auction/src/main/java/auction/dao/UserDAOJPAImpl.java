package auction.dao;

import auction.domain.User;

import javax.persistence.*;
import java.util.List;

public class UserDAOJPAImpl implements UserDAO {

    // EntityManager
    EntityManager entityManager;

    public UserDAOJPAImpl(EntityManager em) {
        entityManager = em;
    }

    @Override
    public int count() {
        Query q = entityManager.createNamedQuery("User.count");
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public void create(User user) {
        entityManager.getTransaction().begin();

         if (findByEmail(user.getEmail()) != null) {
            throw new EntityExistsException();
        }

        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public void edit(User user) {
        entityManager.getTransaction().begin();

        if (findByEmail(user.getEmail()) == null) {
            throw new IllegalArgumentException();
        }

        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<User> findAll() {
        Query q = entityManager.createNamedQuery("User.getAll");
        return q.getResultList();
    }

    @Override
    public User findByEmail(String email) {
        User foundUser = null;
        Query q = entityManager.createNamedQuery("User.findByEmail", User.class);
        q.setParameter("email", email);

        try {
            foundUser = (User) q.getSingleResult();
        }

        catch (NoResultException ex) {
            System.out.println(ex.getMessage());
        }

        return foundUser;
    }

    @Override
    public void remove(User user) {
        entityManager.getTransaction().begin();
        entityManager.remove((entityManager.merge(user)));
        entityManager.getTransaction().commit();
    }
}
