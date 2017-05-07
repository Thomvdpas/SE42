package bank.domain;

import bank.dao.AccountDAOJPAImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Martijn on 02-05-17.
 */
public class AccountTest {

    EntityManagerFactory emf;
    EntityManager em;
    DatabaseCleaner dbCleaner;

    @Before
    public void setup() {
        emf = Persistence.createEntityManagerFactory("bankPU");
        em = emf.createEntityManager();

        dbCleaner = new DatabaseCleaner(em);
        try {
            dbCleaner.clean();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            em = emf.createEntityManager();
        }
    }

    @After
    public void after(){
    }

    @Test
    public void AssignmentOne() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        //Account wordt gepersist. Instantie wordt aangemaakt, maar komt nog niet in de database.

        assertNotNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());

        //Pas bij de commit wordt het naar de database geschreven.
        assertTrue(account.getId() > 0L);
    }

    @Test
    public void AssignmentTwo() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);
        assertNotNull(account.getId());
        em.getTransaction().rollback();
        // Maak gebruik van de findAll methode. Hiermee worden alle accounts opgehaald en in een lijst gezet.
        // Ik check dan of deze grootte 0 is, dan is de lijst leeg.

        AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);
        List<Account> findAll = accountDAOJPA.findAll();
        assertEquals(findAll.size(), 0);
    }

    @Test
    public void AssignmentThree() {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);
        em.getTransaction().begin();
        em.persist(account);
        //TODO: verklaar en pas eventueel aan
        //Hier moest nog een ) achter.
        assertEquals(expected, account.getId());
        em.flush();
        //TODO: verklaar en pas eventueel aan

        //Hier moest ook nog een ) achter.
        //Na de flush heeft account een echt ID gekregen
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
    }

    @Test
    public void AssignmentFour() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        assertEquals(expectedBalance, account.getBalance());
		// account.getBalance zou de waarde van 400 moeten hebben.
        Long acId = account.getId();

        account = null;
        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);
		// account.getBalance() zou 400 moeten hebben.
        assertEquals(expectedBalance, found.getBalance());
    }

    @Test
    public void AssignmentFive() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);
        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();
        Long acId = account.getId();

        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, acId);

        em2.persist(found);
        found.setBalance(451L);
        em2.getTransaction().commit();

        EntityManager em3 = emf.createEntityManager();
        em3.getTransaction().begin();
        account = em3.find(Account.class, acId);

        assertEquals(account.getBalance(), found.getBalance());
    }

}
