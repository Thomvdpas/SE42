package auction.domain;

import javax.persistence.Entity;

/**
 * Created by Martijn on 28-05-17.
 */
@Entity
public class Painting extends Item {

    String title;
    String painter;

    public Painting() {
        // Empty constructor
    }

    public Painting(User seller, Category category, String description, String title, String painter) {
        super(seller, category, description);
        this.title = title;
        this.painter = painter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }

}
