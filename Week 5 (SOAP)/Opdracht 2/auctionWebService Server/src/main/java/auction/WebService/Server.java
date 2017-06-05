/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.WebService;

import javax.xml.ws.Endpoint;

/**
 *
 * @author Martijn
 */
public class Server {
    
    private static final String registrationUrl = "http://localhost:8080/Registration";
    private static final String auctionUrl = "http://localhost:8080/Auction";
    
    public static void main(String[] args) {
        
        Endpoint.publish(registrationUrl, new Registration());
        Endpoint.publish(auctionUrl, new Auction());

    }
    
}
