package auction.service;

import java.util.*;

import auction.dao.UserDAOJPAImpl;
import auction.domain.User;
import auction.dao.UserDAOCollectionImpl;
import auction.dao.UserDAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class RegistrationMgr {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Auction");
    private EntityManager entityManager;

    private UserDAO UserDAO;

    public RegistrationMgr() {
        entityManager = entityManagerFactory.createEntityManager();
        UserDAO = new UserDAOJPAImpl(entityManager);
    }

    /**
     * Registreert een gebruiker met het als parameter gegeven e-mailadres, mits
     * zo'n gebruiker nog niet bestaat.
     * @param email
     * @return Een Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres (nieuw aangemaakt of reeds bestaand). Als het e-mailadres
     * onjuist is ( het bevat geen '@'-teken) wordt null teruggegeven.
     */
    public User registerUser(String email) {

        if (!email.contains("@")) {
            return null;
        }
        User user = UserDAO.findByEmail(email);
        if (user != null) {
            return user;
        }

        user = new User(email);
        UserDAO.create(user);
        return user;

    }

    /**
     *
     * @param email een e-mailadres
     * @return Het Userobject dat geïdentificeerd wordt door het gegeven
     * e-mailadres of null als zo'n User niet bestaat.
     */
    public User getUser(String email) {
        return UserDAO.findByEmail(email);
    }

    /**
     * @return Een iterator over alle geregistreerde gebruikers
     */
    public List<User> getUsers() {
        return UserDAO.findAll();
    }
}
