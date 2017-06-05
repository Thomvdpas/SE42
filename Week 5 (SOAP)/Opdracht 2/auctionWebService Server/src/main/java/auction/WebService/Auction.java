/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auction.WebService;

import auction.domain.Bid;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;
import auction.service.AuctionMgr;
import auction.service.SellerMgr;
import java.util.List;
import javax.jws.WebService;
import nl.fontys.util.Money;

/**
 *
 * @author Martijn
 */
@WebService
public class Auction 
{
    
    AuctionMgr auctionManager;
    SellerMgr sellerManager;
            
    /**
     * Constructor
     */
    public Auction()
    {
        auctionManager = new AuctionMgr();
        sellerManager = new SellerMgr();
    }
    
    /**
     * Function to get a item by it's ID
     * @param id ID of the needed item
     * @return the corresponding item by it's ID
     */
    public Item getItem(Long id)
    {
        return auctionManager.getItem(id);
    }
    
    /**
     * Function to get items by description
     * @param description the description
     * @return a list of items that has the same description as given
     */
    public List<Item> findItemByDescription(String description)
    {
        return auctionManager.findItemByDescription(description);
    }
    
    /**
     * Function to make a new bid on a certain item
     * @param item the item of interest
     * @param buyer the buyer
     * @param amount the amount of the new bid
     * @return returns the bid object
     */
    public Bid newBid(Item item, User buyer, Money amount)
    {
        return auctionManager.newBid(item, buyer, amount);
    }
    
    /**
     * Function to offer a specific item
     * @param seller the seller of the item
     * @param cat the category where the item belongs
     * @param description description of the item
     * @return the item
     */
    public Item offerItem(User seller, Category cat, String description)
    {
        return sellerManager.offerItem(seller, cat, description);
    }
    
    /**
     * Function to revoke a item from auction
     * @param item the item that needs to be revoked
     * @return a boolean (true or false)
     */
    public boolean revokeItem(Item item)
    {
        return sellerManager.revokeItem(item);
    }
    
    
    
}
