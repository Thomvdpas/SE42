/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAP_Tests;

import auction.webservice.User;
import java.util.List;
import managers.RegistrationManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martijn
 */
public class RegistrationSOAPTest {
   
    private final RegistrationManager registrationManager;
    
    public RegistrationSOAPTest() {
        registrationManager = new RegistrationManager();
    }

    @Test
    public void registerUser() {
        User user1 = registrationManager.registerUser("xxx1@yyy");
        assertTrue(user1.getEmail().equals("xxx1@yyy"));
        User user2 = registrationManager.registerUser("xxx2@yyy2");
        assertTrue(user2.getEmail().equals("xxx2@yyy2"));
        User user2bis = registrationManager.registerUser("xxx2@yyy2");
        assertEquals(user2bis.getEmail(), user2.getEmail());
        
        //geen @ in het adres
        assertNull(registrationManager.registerUser("abc"));
    }

    @Test
    public void getUser() {
        User user1 = registrationManager.registerUser("xxx5@yyy5");
        User userGet = registrationManager.getUser("xxx5@yyy5");
        assertEquals(userGet.getEmail(), user1.getEmail());
        
        assertNull(registrationManager.getUser("aaa4@bb5"));
        registrationManager.registerUser("abc");
        assertNull(registrationManager.getUser("abc"));
    }
    
    @Test
    public void getUsers() {
        List<User> users = registrationManager.getUsers();
        assertEquals(3, users.size());

        User user1 = registrationManager.registerUser("xxx8@yyy");
        users = registrationManager.getUsers();
        assertEquals(4, users.size());
        //assertEquals(users.get(0), user1);


        User user2 = registrationManager.registerUser("xxx9@yyy");
        users = registrationManager.getUsers();
        assertEquals(5, users.size());

        registrationManager.registerUser("abc");
        // geen nieuwe user toegevoegd, dus gedrag hetzelfde als hiervoor
        users = registrationManager.getUsers();
        assertEquals(5, users.size());
    }
}
