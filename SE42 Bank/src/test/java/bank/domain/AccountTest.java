package bank.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

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
    }

    @After
    public void clean() throws SQLException {
        dbCleaner.clean();
    }

    @Test
    public void AssigmentOne() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);

        //TODO: verklaar en pas eventueel aan
        assertNull(account.getId());
        em.getTransaction().commit();
        System.out.println("AccountId: " + account.getId());

        //TODO: verklaar en pas eventueel aan
        assertTrue(account.getId() > 0L);
    }

}
