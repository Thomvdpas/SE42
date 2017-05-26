package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.dao.UserDAO;
import auction.dao.UserDAOJPAImpl;
import nl.fontys.util.Money;
import auction.domain.Bid;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class AuctionMgr  {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Auction");
    private EntityManager entityManager;
    private ItemDAOJPAImpl ItemDAO;

    public AuctionMgr() {
        entityManager = entityManagerFactory.createEntityManager();
        ItemDAO = new ItemDAOJPAImpl(entityManager);
    }

   /**
     * @param id
     * @return het item met deze id; als dit item niet bekend is wordt er null
     *         geretourneerd
     */
    public Item getItem(Long id) {
        return ItemDAO.find(id);
    }

  
   /**
     * @param description
     * @return een lijst met items met @desciption. Eventueel lege lijst.
     */
    public List<Item> findItemByDescription(String description) {
        return ItemDAO.findByDescription(description);
    }

    /**
     * @param item
     * @param buyer
     * @param amount
     * @return het nieuwe bod ter hoogte van amount op item door buyer, tenzij
     *         amount niet hoger was dan het laatste bod, dan null
     */
    public Bid newBid(Item item, User buyer, Money amount) {

        Bid newBid = null;

        // Check if new bid is higher then existing highest bid on the specific item
        if (item.getHighestBid() == null || item.getHighestBid().getAmount().getCents() < amount.getCents())
        {
            item.newBid(buyer, amount);
            newBid = new Bid(buyer, amount);
            ItemDAO.edit(item); // Update item with the new bid
        }

        return newBid;

    }
}
