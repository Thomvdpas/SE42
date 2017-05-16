package bank.domain;

import bank.dao.AccountDAOJPAImpl;
import org.junit.Before;
import org.junit.Test;
import util.DatabaseCleaner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

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
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        finally {
            em = emf.createEntityManager();
        }
    }

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // assertNull moet worden aangepast naar assertNotNull.
    //
    // Welke SQL-statements worden gegenereerd?
    // INSERT INTO
    // SELECT
    //
    // Wat is het eindresultaat in de database?
    // Het account object wordt in de database gezet en dankzij de GeneratedValue annotatie wordt het lege ID omgezet naar een auto incremented ID (dus 1).
    //
    // Verklaring van bovenstaande drie observaties.
    // De assertNull moet worden aangepast naar de assertNotNull aangezien het aanroepen van de persist functie ervoor
    // zorgt dat het object wordt gevuld met de nieuwe waarde (in dit geval een auto incremented ID).
    @Test
    public void AssignmentOne() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);  // Account wordt gepersist. Instantie wordt aangemaakt, maar komt nog niet in de database.

        System.out.println(account.getId());
        assertNotNull(account.getId());
        em.getTransaction().commit();

        // Pas bij de commit wordt het naar de database geschreven!
        assertTrue(account.getId() > 0L);
    }

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // assertNull moet worden aangepast naar assertNotNull. Ook de code om te controleren of het aantal records in de database 0 zijn moest worden gemaakt.
    //
    // Welke SQL-statements worden gegenereerd?
    // SELECT
    // COUNT
    //
    // Wat is het eindresultaat in de database?
    // Niets, aangezien er een rollback wordt uitgevoerd en niets wordt gecommit.
    //
    // Verklaring van bovenstaande drie observaties.
    // De assertNull moet worden aangepast naar de assertNotNull aangezien het aanroepen van de persist functie
    // ervoor zorgt dat het object wordt gevuld met de nieuwe waarde (in dit geval een auto incremented ID).
    @Test
    public void AssignmentTwo() {
        Account account = new Account(111L);
        em.getTransaction().begin();
        em.persist(account);

        assertNotNull(account.getId());

        em.getTransaction().rollback(); // Rollback

        // Maak gebruik van de count methode. Hiermee worden het aantal records opgehaald.
        AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);
        assertEquals(accountDAOJPA.count(), 0);
    }

    @Test
    public void AssignmentThree() {
        Long expected = -100L;
        Account account = new Account(111L);
        account.setId(expected);

        em.getTransaction().begin();
        em.persist(account);

        //TODO: verklaar en pas eventueel aan
        //Hier moest nog een ')' achter.
        assertEquals(expected, account.getId());

        em.flush();

        //TODO: verklaar en pas eventueel aan
        // Hier moest ook nog een ) achter.
        // Na de flush heeft account een echt ID gekregen
        assertNotEquals(expected, account.getId());
        em.getTransaction().commit();
    }

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // Corrigeren van verkeerde asserties waren niet nodig.
    //
    // Welke SQL-statements worden gegenereerd?
    // INSERT INTO
    // SELECT WHERE
    //
    // Wat is het eindresultaat in de database?
    // Er staat nu een account in de database met het accountnummer 114, balance van 400 en een treshold van 0.
    //
    // Verklaring van bovenstaande drie observaties.
    // Eerst wordt er een account gemaakt en vervolgens wordt deze opgeslagen in de database. Daarna wordt
    // het lokale account veranderd in dat van het account wat in de database wordt gevonden (ID 1).
    // Om deze reden krijgt het lokale object ‘found’ dezelfde waardes als het object in de database.
    @Test
    public void AssignmentFour() {
        Long expectedBalance = 400L;
        Account account = new Account(114L);

        em.getTransaction().begin();
        em.persist(account);
        account.setBalance(expectedBalance);
        em.getTransaction().commit();

        assertEquals(expectedBalance, account.getBalance());

        Long accountId = account.getId();
        account = null;

        EntityManager em2 = emf.createEntityManager();
        em2.getTransaction().begin();
        Account found = em2.find(Account.class, accountId);

		// found.getBalance() zou 400 moeten zijn
        assertEquals(expectedBalance, found.getBalance());
    }

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // Corrigeren van verkeerde asserties waren niet nodig.
    //
    // Welke SQL-statements worden gegenereerd?
    // INSERT INTO
    // SELECT
    // UPDATE
    //
    // Wat is het eindresultaat in de database?
    // Er staat nu een account in de database met het accountnummer 114, balance van 451 en een threshold van 0.
    //
    // Verklaring van bovenstaande drie observaties.
    // We halen het nieuwe account op uit de database en deze heeft dezelfde balance als het andere account dus slaagt de assert functie.
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

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // Corrigeren van verkeerde asserties waren niet nodig.
    //
    // Welke SQL-statements worden gegenereerd?
    // INSERT INTO
    // SELECT WHERE
    // UPDATE
    //
    // Wat is het eindresultaat in de database?
    // Er zijn in totaal 4 accounts toegevoegd.
    //
    // Verklaring van bovenstaande drie observaties.
    // Staat in de comments.
    @Test
    public void AssignmentSix(){
        Account acc = new Account(1L);
        Account acc2 = new Account(2L);
        Account acc9 = new Account(9L);

        // New AccountDOAJPAImpl
        AccountDAOJPAImpl accountDAOJPA = new AccountDAOJPAImpl(em);

        // Scenario 1
        Long balance1 = 100L;
        em.getTransaction().begin();
        em.persist(acc);
        acc.setBalance(balance1);
        em.getTransaction().commit();

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifieren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        assertEquals(balance1, acc.getBalance());
        assertEquals(balance1, em.find(Account.class, acc.getId()).getBalance());

        // Scenario 2
        Long balance2a = 211L;
        acc = new Account(2L);
        em.getTransaction().begin();
        acc9 = em.merge(acc);
        acc.setBalance(balance2a);
        acc9.setBalance(balance2a + balance2a);
        em.getTransaction().commit();

        Long balance2b = balance2a * 2;

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        // HINT: gebruik acccountDAO.findByAccountNr
        assertEquals(acc9, accountDAOJPA.findByAccountNr(2L));
        assertEquals(balance2a, acc.getBalance());
        assertEquals(balance2b, acc9.getBalance());
        assertEquals(balance2b, accountDAOJPA.findByAccountNr(2L).getBalance());

        // Scenario 3
        Long balance3b = 322L;
        Long balance3c = 333L;
        acc = new Account(3L);
        em.getTransaction().begin();
        acc2 = em.merge(acc);

        assertFalse(em.contains(acc));  // Verklaar -> EM geeft niets om acc omdat die hem niet moet kennen
        assertTrue(em.contains(acc2));  // Verklaar -> EM kent hier acc2 wel omdat dankzij merge het acc2 object nu herkend is
        assertNotEquals(acc,acc2);      // Verklaar -> Objecten zijn niet gelijk omdat acc geen ID kent (niet bekend is in DB)

        acc2.setBalance(balance3b);
        acc.setBalance(balance3c);
        em.getTransaction().commit();

        //TODO: voeg asserties toe om je verwachte waarde van de attributen te verifiëren.
        //TODO: doe dit zowel voor de bovenstaande java objecten als voor opnieuw bij de entitymanager opgevraagde objecten met overeenkomstig Id.
        assertEquals(balance3b, accountDAOJPA.findByAccountNr(3L).getBalance());

        // Scenario 4
        Account account = new Account(114L);
        account.setBalance(450L);

        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();

        Account account2 = new Account(114L);
        Account tweedeAccountObject = account2;
        tweedeAccountObject.setBalance(650l);
        assertEquals((Long) 650L, account2.getBalance());   // tweedeAccountObject is een reference naar account2

        account2.setId(account.getId());
        em.getTransaction().begin();
        account2 = em.merge(account2);

        assertSame(account, account2);                      // Account2 heeft hetzelfde ID als account gekregen (verwijzen naar elkaar na de merge aangezien het ID van account2 gelijk is aan account.getID)
        assertTrue(em.contains(account2));                  // De EM kent Account2 omdat account2 gelijk is aan account (die is bekend in de DB)
        assertFalse(em.contains(tweedeAccountObject));      // tweedeAccountObject is zijn reference kwijt naar account2 (ivm merge) .. Dus de EM kent het tweedeAccountobject niet
        tweedeAccountObject.setBalance(850l);
        assertEquals((Long) 650L, account.getBalance());    // Account2 en account zijn onder water hetzelfde object. De balance (die is geset door tweedeAccountObject) geld nog in de DB
        assertEquals((Long) 650L, account2.getBalance());   // Account2 en account zijn onder water hetzelfde object. De balance (die is geset door tweedeAccountObject) geld nog in de DB

        em.getTransaction().commit();
        em.close();
    }

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // De tweede assertSame moest worden aangepast naar assertNotSame ivm de em.clear()
    //
    // Welke SQL-statements worden gegenereerd?
    // INSERT INTO
    // SELECT WHERE
    //
    // Wat is het eindresultaat in de database?
    // Er is in totaal 1 account toegevoegd.
    //
    // Verklaring van bovenstaande drie observaties.
    // Staat in de comments.
    @Test
    public void AssignmentSeven() {
        Account acc1 = new Account(77L);

        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit(); // Database bevat nu een account.

        // Scenario 1
        Account accF1;
        Account accF2;

        accF1 = em.find(Account.class, acc1.getId());
        accF2 = em.find(Account.class, acc1.getId());
        assertSame(accF1, accF2);

        // Scenario 2
        accF1 = em.find(Account.class, acc1.getId());
        em.clear();
        accF2 = em.find(Account.class, acc1.getId());
        assertNotSame(accF1, accF2); // assertSame -> assertNotSame

        // TODO verklaar verschil tussen beide scenario’s
        // Verschil is dat de EM wordt gecleared (dus de acc1 is niet meer bekend in de context)
    }

    // Wat is de waarde van asserties en printstatements? Corrigeer verkeerde asserties zodat de test ‘groen’ wordt.
    // Corrigeren van verkeerde asserties waren niet nodig.
    //
    // Welke SQL-statements worden gegenereerd?
    // INSERT INTO
    // SELECT WHERE
    //
    // Wat is het eindresultaat in de database?
    // Er is in totaal 1 account toegevoegd.
    //
    // Verklaring van bovenstaande drie observaties.
    // Staat in de comments.
    @Test
    public void AssignmentEight() {

        Account acc1 = new Account(88L);

        em.getTransaction().begin();
        em.persist(acc1);
        em.getTransaction().commit(); // Database bevat nu een account
        Long id = acc1.getId();

        em.remove(acc1);
        assertEquals(id, acc1.getId());
        Account accFound = em.find(Account.class, id);
        assertNull(accFound);

        // TODO: verklaar bovenstaande asserts
        // acc1 is lokaal nog steeds bekend waardoor de assertEquals gewoon slaagt
        // In de EM is echter acc1 niet meer bekend waardoor die geen account zal teruggeven
    }

    // Wat is het eindresultaat in de database?
    //
    // IDENTITY = IDENTITY generator laat het toe een integer kolom te auto incrementen
    //
    // SEQUENCE = Een SEQUENCE is een object die incrementele integers genereerd op elke succesvolle request.
    // Dit is tevens niet supported met MySQL waardoor wij het verschil niet konden zien in database.
    //
    // TABLE = Zag hier geen verschil in de database. Kan ook geen duidelijke voorbeelden vinden van wat dit is en hoe dit werkt.
    //
    @Test
    public void AssignmentNine() { }

    // OEFENTOETS!

    @Test
    public void test1() {
        Account account = new Account(111L);
        em.getTransaction().begin();

        em.persist(account);
        assertNull(account.getId()); // AssertionError -> assertNotNull
        em.getTransaction().commit();

        assertTrue(account.getId() > 0L);
    }

    @Test
    public void test2() {
        Long expected = 200L;
        Account account = new Account(112L);
        Account merged;
        account.setId(332L);
        em.getTransaction().begin();

        merged = em.merge(account);
        em.getTransaction().commit();
        merged.setBalance(expected);

        em.close();

        assertEquals(expected, merged.getBalance());
        assertSame(account, merged);
    }

    @Test
    public void test3() {
        Long expected = 100L;
        Account account = new Account(111L);
        account.setId(331L);

        Account merged;
        em.getTransaction().begin();

        merged = em.merge(account);
        assertEquals(merged.getId(), account.getId());
        em.getTransaction().commit();
        assertTrue(merged.getId() != account.getId());
        assertFalse(em.contains(merged));
        assertFalse(em.contains(account));

        em.close();

        assertSame(merged, account);
        assertEquals(merged, account);
    }

}
