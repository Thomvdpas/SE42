/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAP_Tests;

import auction.webservice.Category;
import auction.webservice.Item;
import auction.webservice.Money;
import auction.webservice.User;
import managers.AuctionManager;
import managers.RegistrationManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martijn
 */
public class SellerSOAPTest {
    
    private final RegistrationManager registrationManager;
    private final AuctionManager auctionManager;
    
    public SellerSOAPTest() {
        registrationManager = new RegistrationManager();
        auctionManager = new AuctionManager();
    }

    /**
     * Test of offerItem method, of class SellerMgr.
     */
    @Test
    public void testOfferItem() {
        String omsch = "omsch";

        User user1 = registrationManager.registerUser("xx@nl");
        Category cat = new Category("cat1");
        Item item1 = auctionManager.offerItem(user1, cat, omsch);
        
        assertEquals(omsch, item1.getDescription());
        assertNotNull(item1.getId());
    }

    /**
     * Test of revokeItem method, of class SellerMgr.
     */
    @Test
    public void testRevokeItem() {
        String omsch = "omsch";
        String omsch2 = "omsch2";


        User seller = registrationManager.registerUser("sel@nl");
        User buyer = registrationManager.registerUser("buy@nl");
        Category cat = new Category("cat1");

        // revoke before bidding
        Item item1 = auctionManager.offerItem(seller, cat, omsch);
        boolean res = auctionManager.revokeItem(item1);
        assertTrue(res);
        int count = auctionManager.findItemByDescription(omsch).size();
        assertEquals(1, count);

        // revoke after bid has been made
        Item item2 = auctionManager.offerItem(seller, cat, omsch2);
        auctionManager.newBid(item2, buyer, new Money(100, "Euro"));
        boolean res2 = auctionManager.revokeItem(item2);
        assertTrue(res2);
        int count2 = auctionManager.findItemByDescription(omsch2).size();
        assertEquals(0, count2);
    }
    
}
