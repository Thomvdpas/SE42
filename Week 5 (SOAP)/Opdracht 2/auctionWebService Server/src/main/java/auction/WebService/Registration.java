/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.WebService;

import auction.domain.User;
import auction.service.RegistrationMgr;
import javax.jws.WebService;

/**
 *
 * @author Martijn
 */
@WebService
public class Registration 
{
    
    private RegistrationMgr registrationManager;
    
    /**
     * Constructor
     */
    public Registration() 
    {    
        registrationManager = new RegistrationMgr();
    }
    
    /**
     * Function to register a user by it's email adress
     * @param email is the email adress of the user
     * @return the user object
     */
    public User registerUser(String email)
    {
        return registrationManager.registerUser(email);
    }
    
    /**
     * Function to get a user by it's email adress
     * @param email email adress of the user that needs to be found
     * @return the corresponding user by email
     */
    public User getUser(String email)
    {
        return registrationManager.getUser(email);
    }
    
}
