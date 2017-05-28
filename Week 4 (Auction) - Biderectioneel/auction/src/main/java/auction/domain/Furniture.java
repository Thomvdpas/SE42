package auction.domain;

import javax.persistence.Entity;

/**
 * Created by Martijn on 28-05-17.
 */
@Entity
public class Furniture extends Item {

    String material;

    public Furniture() {
        // Empty constructor
    }

    public Furniture(String _material, User seller, Category category, String description) {
        super(seller, category, description);
        this.material = _material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

}
