package auction.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@NamedQueries({
    @NamedQuery(name = "User.count", query = "Select count(user) from User as user"),
    @NamedQuery(name = "User.getAll", query = "select user from User as user"),
    @NamedQuery(name = "User.findByEmail", query = "select user from User as user where user.email = :email")
})
public class User implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @OneToMany
    private Set<Item> offeredItems;

    public User() {
        // Empty constructor
    }

    public User(String email) {
        this.email = email;
        this.offeredItems = new HashSet<Item>();
    }

    public String getEmail() {
        return email;
    }

    public Iterator getOfferedItems() {return offeredItems.iterator(); }

    public void addItem(Item item) {offeredItems.add(item); }

    public int numberOfOfferedItems() {
        return offeredItems.size();
    }
}
