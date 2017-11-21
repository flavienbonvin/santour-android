package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by flavien on 11/21/17.
 */

@IgnoreExtraProperties
public class CategoryPOD {

    @Exclude
    private String id;
    private String name;
    private int rating;

    public CategoryPOD() { }

    public CategoryPOD(String id, String name, int rating) {
        this.id = id;
        this.name = name;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
