/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auctionwebservice.client;

import Registration.User;

/**
 *
 * @author Martijn
 */
public class AuctionWebServiceClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(registerUser("test@test.nl"));
    }

    private static User getUser(java.lang.String arg0) {
        Registration.RegistrationService service = new Registration.RegistrationService();
        Registration.Registration port = service.getRegistrationPort();
        return port.getUser(arg0);
    }

    private static User registerUser(java.lang.String arg0) {
        Registration.RegistrationService service = new Registration.RegistrationService();
        Registration.Registration port = service.getRegistrationPort();
        return port.registerUser(arg0);
    }
    
}
