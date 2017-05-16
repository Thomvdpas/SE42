package auction.domain;

import javax.persistence.*;
import java.io.Serializable;

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

    public User() {
        // Empty constructor
    }

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
