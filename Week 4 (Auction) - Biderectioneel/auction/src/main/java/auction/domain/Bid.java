package auction.domain;

import nl.fontys.util.FontysTime;
import nl.fontys.util.Money;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Bid implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private FontysTime time;

    @ManyToOne
    private User buyer;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="currency", column=@Column(name = "currency")),
            @AttributeOverride(name="cents", column=@Column(name = "amountOfCents"))
    })
    private Money amount;

    @OneToOne
    private Item item;

    public Bid() {
        // Empty Constructor
    }

    public Bid(User buyer, Money amount) {
        this.buyer = buyer;
        this.amount = amount;
        this.time = new FontysTime();
        this.item = null;
    }

    public FontysTime getTime() {
        return time;
    }

    public User getBuyer() {
        return buyer;
    }

    public Money getAmount() {
        return amount;
    }

    public Item getItem() {
        return item;
    }
}
