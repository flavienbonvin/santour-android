package ch.hesso.santour.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by flavien on 11/21/17.
 */

@IgnoreExtraProperties
public class CategoryPOI {

    @Exclude
    private String id;
    private String name;

    public CategoryPOI() { }

    public CategoryPOI(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
