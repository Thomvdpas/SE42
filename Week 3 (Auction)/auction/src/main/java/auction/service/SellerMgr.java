package auction.service;

import auction.dao.ItemDAO;
import auction.dao.ItemDAOJPAImpl;
import auction.domain.Category;
import auction.domain.Item;
import auction.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SellerMgr {

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Auction");
    private EntityManager entityManager;
    private ItemDAOJPAImpl ItemDAO;

    public SellerMgr() {
        entityManager = entityManagerFactory.createEntityManager();
        ItemDAO = new ItemDAOJPAImpl(entityManager);
    }

    /**
     * @param seller
     * @param cat
     * @param description
     * @return het item aangeboden door seller, behorende tot de categorie cat
     *         en met de beschrijving description
     */
    public Item offerItem(User seller, Category cat, String description) {
        Item newOfferItem = new Item(seller, cat, description);
        ItemDAO.create(newOfferItem);
        return newOfferItem;
    }
    
     /**
     * @param item
     * @return true als er nog niet geboden is op het item. Het item word verwijderd.
     *         false als er al geboden was op het item.
     */
    public boolean revokeItem(Item item) {

        if(item.getHighestBid() == null) {
            ItemDAO.remove(item);
            return true;
        }

        else {
            return false;
        }

    }
}
