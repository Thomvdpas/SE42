/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SOAP_Tests;

import auction.webservice.Bid;
import auction.webservice.Category;
import auction.webservice.Item;
import auction.webservice.Money;
import auction.webservice.User;
import java.util.ArrayList;
import managers.AuctionManager;
import managers.RegistrationManager;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martijn
 */
public class AuctionSOAPTest {
    
    private final AuctionManager auctionManager;
    private final RegistrationManager registrationManager;
    
    public AuctionSOAPTest() {
        auctionManager = new AuctionManager();
        registrationManager = new RegistrationManager();
    }
    
    @Test
    public void getItem() {
        String email = "xx2@nl";
        String omsch = "omsch";

        User seller1 = registrationManager.registerUser(email);
        Category cat = new Category("cat2");
        Item item1 = auctionManager.offerItem(seller1, cat, omsch);
        Item item2 = auctionManager.getItem(item1.getId());
        assertEquals(omsch, item2.getDescription());
        assertEquals(email, item2.getSeller().getEmail());
    }

    @Test
    public void findItemByDescription() {
        String email3 = "xx3@nl";
        String omsch = "omsch";
        String email4 = "xx4@nl";
        String omsch2 = "omsch2";

        User seller3 = registrationManager.registerUser(email3);
        User seller4 = registrationManager.registerUser(email4);

        Category cat = new Category("cat3");
        Item item1 = auctionManager.offerItem(seller3, cat, omsch);
        Item item2 = auctionManager.offerItem(seller4, cat, omsch);

        ArrayList<Item> res = (ArrayList<Item>) auctionManager.findItemByDescription(omsch2);
        assertEquals(0, res.size());

        res = (ArrayList<Item>) auctionManager.findItemByDescription(omsch);
        assertEquals(3, res.size());
    }

    @Test
    public void newBid() {
        String email = "ss2@nl";
        String emailb = "bb@nl";
        String emailb2 = "bb2@nl";
        String omsch = "omsch_bb";

        User seller = registrationManager.registerUser(email);
        User buyer = registrationManager.registerUser(emailb);
        User buyer2 = registrationManager.registerUser(emailb2);

        // eerste bod
        Category cat = new Category("cat9");
        Item item1 = auctionManager.offerItem(seller, cat, omsch);
        Bid new1 = auctionManager.newBid(item1, buyer, new Money(10, "eur"));
        assertEquals(emailb, new1.getBuyer().getEmail());

        // lager bod
        Bid new2 = auctionManager.newBid(item1, buyer2, new Money(9, "eur"));
        assertNotNull(new2);

        // hoger bod
        Bid new3 = auctionManager.newBid(item1, buyer2, new Money(11, "eur"));
        assertEquals(emailb2, new3.getBuyer().getEmail());
    }
}
