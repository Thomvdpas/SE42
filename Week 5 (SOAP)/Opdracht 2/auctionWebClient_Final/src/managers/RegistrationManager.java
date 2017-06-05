/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import auction.webservice.Registration;
import auction.webservice.RegistrationService;
import auction.webservice.User;
import java.util.List;

/**
 *
 * @author Martijn
 */
public class RegistrationManager {
    
    private final RegistrationService service;
    Registration port;

    public RegistrationManager() {
        service = new RegistrationService();
        port = service.getRegistrationPort();
    }
    
    public User getUser(String email) {
        return port.getUser(email);
    }
    
    public User registerUser(String email) {
        return port.registerUser(email);
    }
    
    public List<User> getUsers() {
        return port.getUsers();
    }
    
}
