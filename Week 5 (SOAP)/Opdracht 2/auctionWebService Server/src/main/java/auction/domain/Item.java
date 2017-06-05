package auction.domain;

import nl.fontys.util.Money;

import javax.persistence.*;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@NamedQueries({
        @NamedQuery(name = "Item.count", query = "Select count(item) from Item as item"),
        @NamedQuery(name = "Item.getAll", query = "select item from Item as item"),
        @NamedQuery(name = "Item.findByDescription", query = "select item from Item as item where item.description =:description")
})

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item implements Comparable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User seller;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="description", column=@Column(name = "cat_description"))
    })
    private Category category;

    private String description;

    @ManyToOne (cascade = CascadeType.ALL)
    private Bid highest;

    public Item() {
        // Empty Constructor
    }

    public Item(User seller, Category category, String description) {
        this.seller = seller;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public User getSeller() {
        return seller;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public Bid getHighestBid() {
        return highest;
    }

    public Bid newBid(User buyer, Money amount) {
        if (highest != null && highest.getAmount().compareTo(amount) >= 0) {
            return null;
        }
        highest = new Bid(buyer, amount);
        return highest;
    }

    public int compareTo(Object arg0) {
        //TODO
        return -1;
    }

    public boolean equals(Object o) {
        //TODO
        return false;
    }

    public int hashCode() {
        //TODO
        return 0;
    }

}
