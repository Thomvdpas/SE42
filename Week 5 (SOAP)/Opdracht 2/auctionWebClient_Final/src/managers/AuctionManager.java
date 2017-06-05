/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import auction.webservice.Auction;
import auction.webservice.AuctionService;
import auction.webservice.Bid;
import auction.webservice.Category;
import auction.webservice.Item;
import auction.webservice.Money;
import auction.webservice.User;
import java.util.List;

/**
 *
 * @author Martijn
 */
public class AuctionManager {
    
    private AuctionService service;

    public AuctionManager() {
        service = new AuctionService();
    }
    
    public List<Item> findItemByDescription(String desc) {
        Auction port = service.getAuctionPort();
        return port.findItemByDescription(desc);
    }
   
    public Item getItem(Long id) {
        Auction port = service.getAuctionPort();
        return port.getItem(id);
    }
    
    public Item offerItem(User seller, Category cat, String desc) {
        Auction port = service.getAuctionPort();
        return port.offerItem(seller, cat, desc);
    }
    
    public Bid newBid(Item item, User buyer, Money amount) {
        Auction port = service.getAuctionPort();
        return port.newBid(item, buyer, amount);
    }
    
    public boolean revokeItem(Item item) {
        Auction port = service.getAuctionPort();
        return port.revokeItem(item);
    }
    
}
